package com.example.dtt.controller.map;

import com.example.dtt.annotation.DataSource;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.map.Cols;
import com.example.dtt.domain.entity.map.DB;
import com.example.dtt.domain.entity.map.Table;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.DataSourceType;
import com.example.dtt.service.map.ColService;
import com.example.dtt.service.map.DBService;
import com.example.dtt.service.map.TableRelationService;
import com.example.dtt.service.map.TableService;
import com.example.dtt.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 界面展示表信息
 */
@RestController
@RequestMapping("/map/meta")
public class MetaStoreController extends BaseController {

    @Autowired
    private TableService tableService;

    @Autowired
    private DBService dbService;

    @Autowired
    private ColService colService;

    @Autowired
    private TableRelationService relationService;

    /**
     * 获取表列表
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:table:list')")
    @GetMapping("/tableList")
    public TableDataInfo list(Table table) {
        startPage();
        List<Table> list = tableService.selectTableList(table);
        return getDataTable(list);
    }

    /**
     * 获取数据库列表
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/dbList")
    public AjaxResult getDbList(DB db) {
        List<DB> dbs = dbService.selectDBList(db);
        return AjaxResult.success(dbs);
    }

    /**
     * 根据表id获取表字段列表
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/metaTable/{tableId}")
    public AjaxResult getColList(@PathVariable(value = "tableId") Long tableId) {
        AjaxResult ajax = AjaxResult.success();
        Table table = tableService.selectByTableId(tableId);
        List<Cols> cols = colService.selectColByTblId(tableId);
        ajax.put("table", table);
        ajax.put("cols", cols.stream().collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 根据表名获取表字段列表
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/metaTables/{tableName}")
    public AjaxResult getCols(@PathVariable(value = "tableName") String tableName) {
        AjaxResult ajax = AjaxResult.success();
        Table table = tableService.selectByTableName(tableName);
        List<Cols> cols = colService.selectColByTblName(tableName);
        ajax.put("table", table);
        ajax.put("cols", cols.stream().collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 根据表id获取表字段列表
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/cols/{tableId}")
    public AjaxResult cols(@PathVariable(value = "tableId") Long tableId) {
        AjaxResult ajax = AjaxResult.success();
        List<Cols> cols = colService.ColsByTblId(tableId);
        ajax.put("cols", cols.stream().collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 根据表id获取表字段列表
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/tableList/{dbId}")
    public AjaxResult getTableList(@PathVariable(value = "dbId") Long dbId) {
        AjaxResult ajax = AjaxResult.success();
        List<Table> tables = tableService.tableListBydbId(dbId);
        ajax.put("table", tables.stream().collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 根据表名获取表详情
     * @param tableName
     * @return
     */
    @DataSource(DataSourceType.SLAVE)
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/table/{tableName}")
    public AjaxResult getTable(@PathVariable(value = "tableName") String tableName) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isEmpty(tableName)){
            return AjaxResult.error("传入的表名有误，请确认！");
        }
        ajax.put("table", tableService.selectTableByTableName(tableName));
        return ajax;
    }

    /**
     * 根据表名获取表详情
     * @param tableName
     * @return
     */
    @PreAuthorize("@ss.hasPermi('map:meta:query')")
    @GetMapping("/tableRelation/{tableName}")
    public AjaxResult getTableRelationList(@PathVariable(value = "tableName") String tableName) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isEmpty(tableName)){
            return AjaxResult.error("传入的表名有误，请确认！");
        }
        ajax.put("tableDetail", relationService.getTableRelation(tableName));
        return ajax;
    }

}
