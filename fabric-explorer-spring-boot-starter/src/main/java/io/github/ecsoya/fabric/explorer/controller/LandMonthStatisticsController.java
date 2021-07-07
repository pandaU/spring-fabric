package io.github.ecsoya.fabric.explorer.controller;


import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.explorer.model.fabric.FabricLandObject;
import io.github.ecsoya.fabric.explorer.service.FabricLandService;
import io.github.ecsoya.fabric.explorer.util.DateFormatLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * The type User controller.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@RestController
public class LandMonthStatisticsController {

    /**
     * Fabric user service
     */
    private final FabricLandService fabricLandService;
    @Autowired
    public LandMonthStatisticsController(FabricLandService fabricLandService) {
        this.fabricLandService = fabricLandService;
    }

    /**
     * Create response entity.
     *
     * @param request the request
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @PutMapping("landMonthStatistics")
    public ResponseEntity<?> create(@RequestBody FabricLandObject request){
        String current = DateFormatLocal.FM.get().format(new Date());
        request.setCreateTime(current);
        request.setUpdateTime(current);
        fabricLandService.extCreate(request);
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
    @PostMapping("landMonthStatistics")
    public ResponseEntity<?> update(@RequestBody FabricLandObject request){
        String current = DateFormatLocal.FM.get().format(new Date());
        request.setUpdateTime(current);
        fabricLandService.extUpdate(request);
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
    @DeleteMapping("landMonthStatistics/{id}")
    public ResponseEntity<?> del(@PathVariable("id") String id){
        fabricLandService.delete(id, FabricLandObject.TYPE);
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
    @GetMapping("landMonthStatistics/history/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id){
        FabricQueryResponse<List<FabricHistory>> history = fabricLandService.history(id, FabricLandObject.TYPE);
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
    @GetMapping("landMonthStatistics/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") String id){
        FabricBlock block = fabricLandService.extBlock(id, FabricLandObject.TYPE);
        return ResponseEntity.ok(block);
    }

    /**
     * List response entity.
     *
     * @param id       the id
     * @param area      the area
     * @param month     the month
     * @param pageSize the page size
     * @param bookmark the bookmark
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("landMonthStatistics")
    public ResponseEntity<?> list(@RequestParam(required = false) String id,@RequestParam(required = false)String month,@RequestParam(required = false)String area,Integer pageSize ,@RequestParam(required = false,defaultValue = "")String bookmark){
        FabricPaginationQuery<FabricLandObject> query = new FabricPaginationQuery<>();
        query.setType(FabricLandObject.TYPE);
        query.setPageSize(pageSize);
        query.setBookmark(bookmark);
        query.equals("id", id).equals("month", month).like("area", area);
        FabricPagination<FabricLandObject> pagination = fabricLandService.pagination(query);
        query.setBookmark(pagination.getBookmark());
        FabricPagination<FabricLandObject> next = fabricLandService.pagination(query);
        if (CollectionUtils.isEmpty(next.getData())){
            pagination.setBookmark(null);
        }
        return ResponseEntity.ok(pagination);
    }

    /**
     * Size response entity.
     *
     * @param id   the id
     * @param area  the area
     * @param month the month
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("landMonthStatistics/size")
    public ResponseEntity<?> size(@RequestParam(required = false) String id,@RequestParam(required = false)String month,@RequestParam(required = false)String area){
        FabricQuery query = new FabricQuery();
        query.equals("id", id).equals("month", month).like("area", area);
        FabricQueryResponse<Number> response = fabricLandService.count(query);
        return ResponseEntity.ok(response);
    }
}
