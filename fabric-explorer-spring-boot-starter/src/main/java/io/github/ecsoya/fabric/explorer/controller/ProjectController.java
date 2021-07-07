package io.github.ecsoya.fabric.explorer.controller;

import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.explorer.model.fabric.FabricLandObject;
import io.github.ecsoya.fabric.explorer.model.fabric.FabricProjectObject;
import io.github.ecsoya.fabric.explorer.service.FabricLandService;
import io.github.ecsoya.fabric.explorer.service.FabricProjectService;
import io.github.ecsoya.fabric.explorer.util.DateFormatLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * The type Project controller.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@RestController
public class ProjectController {

    private final FabricProjectService fabricProjectService;

    private final FabricLandService fabricLandService;
    @Autowired
    public ProjectController(FabricProjectService fabricProjectService, FabricLandService fabricLandService) {
        this.fabricProjectService = fabricProjectService;
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
    @PutMapping("project")
    public ResponseEntity<?> create(@RequestBody FabricProjectObject request){
        String current = DateFormatLocal.FM.get().format(new Date());
        request.setCreateTime(current);
        request.setUpdateTime(current);
        fabricProjectService.extCreate(request);
        request.getArableLand();
        FabricPaginationQuery<FabricLandObject> query = new FabricPaginationQuery<>();
        query.setType(FabricLandObject.TYPE);
        query.setPageSize(1);
        query.setBookmark("");
        query.equals("id", request.getArea()+request.getMonth());
        FabricPagination<FabricLandObject> pagination = fabricLandService.pagination(query);
        List<FabricLandObject> data = pagination.getData();
        if (!CollectionUtils.isEmpty(data)){
            FabricLandObject object = data.get(0);
            compute(object,request);
            object.setLastProjectArable(request.getArableLand());
            object.setLastProjectName(request.getProjectName());
            object.setLastProjectType(request.getType());
            object.setUpdateTime(current);
            fabricLandService.extUpdate(object);
        }else {
            throw new RuntimeException(request.getArea()+"当月还未初始化月度账本");
        }
        return ResponseEntity.ok().build();
    }

    private void compute(FabricLandObject object, FabricProjectObject request) {
        String type = request.getType();
        switch (type){
            case "agricultural":
                object.setSubTotal(object.getSubTotal() + request.getArableLand());
                object.setAgriculturalChangeScale(object.getAgriculturalChangeScale()  + request.getArableLand());
                object.setDifference(object.getDifference() - request.getArableLand());
                object.setArableLand(object.getArableLand() -  request.getArableLand());
                break;
            case "disasters":
                object.setSubTotal(object.getSubTotal() + request.getArableLand());
                object.setDisasters(object.getDisasters()  + request.getArableLand());
                object.setDifference(object.getDifference() - request.getArableLand());
                object.setArableLand(object.getArableLand() -  request.getArableLand());
                break;
            case "deduct":
                object.setSubTotal(object.getSubTotal() + request.getArableLand());
                object.setLawEnforcementDeduct(object.getLawEnforcementDeduct()  + request.getArableLand());
                object.setDifference(object.getDifference() - request.getArableLand());
                object.setArableLand(object.getArableLand() -  request.getArableLand());
                break;
            case "down":
                object.setAddTotal(object.getAddTotal() + request.getArableLand());
                object.setDown(object.getDown() + request.getArableLand());
                object.setDifference(object.getDifference() + request.getArableLand());
                object.setArableLand(object.getArableLand() + request.getArableLand());
                break;
            case "batchBack":
                object.setAddTotal(object.getAddTotal() + request.getArableLand());
                object.setBatchBack(object.getBatchBack()  + request.getArableLand());
                object.setDifference(object.getDifference() + request.getArableLand());
                object.setArableLand(object.getArableLand() + request.getArableLand());
                break;
            default:
                throw new RuntimeException("当前Type不存在");
        }
    }

    /**
     * Update response entity.
     *
     * @param request the request
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @PostMapping("project")
    public ResponseEntity<?> update(@RequestBody FabricProjectObject request){
        String current = DateFormatLocal.FM.get().format(new Date());
        request.setUpdateTime(current);
        fabricProjectService.extUpdate(request);
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
    @DeleteMapping("project/{id}")
    public ResponseEntity<?> del(@PathVariable("id") String id,String type){
        fabricProjectService.delete(id, type);
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
    @GetMapping("project/{type}/history/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id ,@PathVariable("type")String type){
        FabricQueryResponse<List<FabricHistory>> history = fabricProjectService.history(id,type);
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
    @GetMapping("project/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") String id ,String type){
        FabricBlock block = fabricProjectService.extBlock(id, type);
        return ResponseEntity.ok(block);
    }

    /**
     * List response entity.
     *
     * @param id       the id
     * @param area      the area
     * @param month     the name
     * @param pageSize the page size
     * @param bookmark the bookmark
     * @return the response entity
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    @GetMapping("project")
    public ResponseEntity<?> list(String type,@RequestParam(required = false) String id,@RequestParam(required = false)String month,@RequestParam(required = false)String area,Integer pageSize ,@RequestParam(required = false,defaultValue = "")String bookmark){
        FabricPaginationQuery<FabricProjectObject> query = new FabricPaginationQuery<>();
        query.setType(type);
        query.setPageSize(pageSize);
        query.setBookmark(bookmark);
        query.equals("id", id).equals("month", month).like("area", area).equals(type);
        FabricPagination<FabricProjectObject> pagination = fabricProjectService.pagination(query);
        query.setBookmark(pagination.getBookmark());
        FabricPagination<FabricProjectObject> next = fabricProjectService.pagination(query);
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
    @GetMapping("project/size")
    public ResponseEntity<?> size(String type, @RequestParam(required = false) String id,@RequestParam(required = false)String month,@RequestParam(required = false)String area){
        FabricQuery query = new FabricQuery();
        query.equals("id", id).equals("month", month).like("area", area).equals(type);
        FabricQueryResponse<Number> response = fabricProjectService.count(query);
        return ResponseEntity.ok(response);
    }
}
