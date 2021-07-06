package io.github.ecsoya.fabric.explorer.controller;


import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.bean.FabricObject;
import io.github.ecsoya.fabric.bean.IFabricObject;
import io.github.ecsoya.fabric.explorer.model.UserModel;
import io.github.ecsoya.fabric.explorer.request.UserFabricRequest;
import io.github.ecsoya.fabric.service.IFabricObjectService;
import io.github.ecsoya.fabric.service.IFabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IFabricObjectService iFabricService;

    @PutMapping("user")
    public ResponseEntity<?> create(@RequestBody UserFabricRequest request){
        iFabricService.extCreate(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("user")
    public ResponseEntity<?> update(@RequestBody UserFabricRequest request){
        iFabricService.extUpdate(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> del(@PathVariable("id") String id){
        iFabricService.delete(id,UserFabricRequest.TYPE);
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/history/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id){
        FabricQueryResponse<List<FabricHistory>> history = iFabricService.history(id, UserFabricRequest.TYPE);
        return ResponseEntity.ok(history);
    }

    @GetMapping("user/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") String id){
        FabricBlock block = iFabricService.extBlock(id, UserFabricRequest.TYPE);
        return ResponseEntity.ok(block);
    }

    @GetMapping("user")
    public ResponseEntity<?> list(@RequestParam(required = false) String id,@RequestParam(required = false)Integer age,@RequestParam(required = false)String name,Integer pageSize ,@RequestParam(required = false,defaultValue = "")String mark){
        FabricPaginationQuery<FabricObject> query = new FabricPaginationQuery<>();
        query.setType(UserFabricRequest.TYPE);
        query.setPageSize(pageSize);
        query.setBookmark(mark);
        query.equals("key", id).equals("age", age).like("name", name);
        FabricPagination<FabricObject> pagination = iFabricService.pagination(query);
        return ResponseEntity.ok(pagination);
    }
}
