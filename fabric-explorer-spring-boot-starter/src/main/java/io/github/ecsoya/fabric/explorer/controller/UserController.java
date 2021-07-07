package io.github.ecsoya.fabric.explorer.controller;


import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.explorer.model.fabric.FabricUserObject;
import io.github.ecsoya.fabric.explorer.service.FabricUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * The type User controller.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@RestController
public class UserController {

    /**
     * Fabric user service
     */
    @Autowired
    private FabricUserService fabricUserService;

    /**
     * Create response entity.
     *
     * @param request the request
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @PutMapping("user")
    public ResponseEntity<?> create(@RequestBody FabricUserObject request){
        fabricUserService.extCreate(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Update response entity.
     *
     * @param request the request
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @PostMapping("user")
    public ResponseEntity<?> update(@RequestBody FabricUserObject request){
        fabricUserService.extUpdate(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Del response entity.
     *
     * @param id the id
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> del(@PathVariable("id") String id){
        fabricUserService.delete(id, FabricUserObject.TYPE);
        return ResponseEntity.ok().build();
    }

    /**
     * Update response entity.
     *
     * @param id the id
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("user/history/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id){
        FabricQueryResponse<List<FabricHistory>> history = fabricUserService.history(id, FabricUserObject.TYPE);
        return ResponseEntity.ok(history);
    }

    /**
     * Block response entity.
     *
     * @param id the id
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("user/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") String id){
        FabricBlock block = fabricUserService.extBlock(id, FabricUserObject.TYPE);
        return ResponseEntity.ok(block);
    }

    /**
     * List response entity.
     *
     * @param id       the id
     * @param age      the age
     * @param name     the name
     * @param pageSize the page size
     * @param bookmark the bookmark
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("user")
    public ResponseEntity<?> list(@RequestParam(required = false) String id,@RequestParam(required = false)Integer age,@RequestParam(required = false)String name,Integer pageSize ,@RequestParam(required = false,defaultValue = "")String bookmark){
        FabricPaginationQuery<FabricUserObject> query = new FabricPaginationQuery<>();
        query.setType(FabricUserObject.TYPE);
        query.setPageSize(pageSize);
        query.setBookmark(bookmark);
        query.equals("key", id).equals("age", age).like("name", name);
        FabricPagination<FabricUserObject> pagination = fabricUserService.pagination(query);
        query.setBookmark(pagination.getBookmark());
        FabricPagination<FabricUserObject> next = fabricUserService.pagination(query);
        if (CollectionUtils.isEmpty(next.getData())){
            pagination.setBookmark(null);
        }
        return ResponseEntity.ok(pagination);
    }

    /**
     * Size response entity.
     *
     * @param id   the id
     * @param age  the age
     * @param name the name
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("user/size")
    public ResponseEntity<?> size(@RequestParam(required = false) String id,@RequestParam(required = false)Integer age,@RequestParam(required = false)String name){
        FabricQuery query = new FabricQuery();
        query.equals("key", id).equals("age", age).like("name", name);
        FabricQueryResponse<Number> response = fabricUserService.count(query);
        return ResponseEntity.ok(response);
    }
}
