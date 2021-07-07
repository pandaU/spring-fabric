package io.github.ecsoya.fabric.explorer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricQueryResponse;
import io.github.ecsoya.fabric.bean.FabricBlock;
import io.github.ecsoya.fabric.bean.FabricHistory;
import io.github.ecsoya.fabric.bean.FabricLedger;
import io.github.ecsoya.fabric.bean.FabricTransaction;
import io.github.ecsoya.fabric.explorer.FabricExplorerProperties;
import io.github.ecsoya.fabric.service.IFabricInfoService;

/**
 * <p>
 * The type Fabric explorer controller.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class FabricExplorerController {

	/**
	 * Fabric service
	 */
	@Autowired
	private IFabricInfoService fabricService;

	/**
	 * Properties
	 */
	@Autowired
	private FabricExplorerProperties properties;

	/**
	 * Base url string.
	 *
	 * @param request the request
	 * @return the string
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	private String baseUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		return scheme + "://" + serverName + ":" + port + path;
	}

	/**
	 * Home model and view.
	 *
	 * @param request the request
	 * @return the model and view
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("explorer/index");
		model.addAllObjects(properties.toMap());
		model.addObject("baseURL", baseUrl(request));
		return model;
	}

	/**
	 * Block model and view.
	 *
	 * @param request the request
	 * @param height  the height
	 * @return the model and view
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/block")
	public ModelAndView block(HttpServletRequest request, long height) {
		ModelAndView model = new ModelAndView("explorer/block");
		model.addAllObjects(properties.toMap());
		model.addObject("height", height);
		model.addObject("baseURL", baseUrl(request));
		return model;
	}

	/**
	 * Tx model and view.
	 *
	 * @param request the request
	 * @param txid    the txid
	 * @return the model and view
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/tx")
	public ModelAndView tx(HttpServletRequest request, String txid) {
		ModelAndView model = new ModelAndView("explorer/tx");
		model.addAllObjects(properties.toMap());
		model.addObject("txid", txid);
		model.addObject("baseURL", baseUrl(request));
		return model;
	}

	/**
	 * History model and view.
	 *
	 * @param request the request
	 * @param key     the key
	 * @param type    the type
	 * @return the model and view
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@RequestMapping("/history")
	public ModelAndView history(HttpServletRequest request, String key, String type) {
		ModelAndView model = new ModelAndView("explorer/history");
		model.addAllObjects(properties.toMap());
		model.addObject("key", key);
		model.addObject("type", type);
		model.addObject("baseURL", baseUrl(request));
		return model;
	}

	/**
	 * Query fabric ledger fabric query response.
	 *
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@PostMapping("/query/ledger")
	@ResponseBody
	public FabricQueryResponse<FabricLedger> queryFabricLedger() {
		return fabricService.queryFabricLedger();
	}

	/**
	 * Query blocks fabric pagination.
	 *
	 * @param query the query
	 * @return the fabric pagination
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@PostMapping("/query/blockpage")
	@ResponseBody
	public FabricPagination<FabricBlock> queryBlocks(@RequestBody FabricPaginationQuery<FabricBlock> query) {
		return fabricService.queryBlocks(query);
	}

	/**
	 * Query block info fabric query response.
	 *
	 * @param blockNumber the block number
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/query/block/{number}")
	@ResponseBody
	public FabricQueryResponse<FabricBlock> queryBlockInfo(@PathVariable("number") long blockNumber) {
		return fabricService.queryBlockByNumber(blockNumber);
	}

	/**
	 * Query transaction info fabric query response.
	 *
	 * @param txid the txid
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/query/tx/{id}")
	@ResponseBody
	public FabricQueryResponse<FabricTransaction> queryTransactionInfo(@PathVariable("id") String txid) {
		return fabricService.queryTransaction(txid);
	}

	/**
	 * Query transactions fabric query response.
	 *
	 * @param blockNumber the block number
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/query/transactions/{number}")
	@ResponseBody
	public FabricQueryResponse<List<FabricTransaction>> queryTransactions(@PathVariable("number") long blockNumber) {
		return fabricService.queryTransactions(blockNumber);
	}

	/**
	 * Query transaction rw set fabric query response.
	 *
	 * @param txid the txid
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/query/txrw/{id}")
	@ResponseBody
	public FabricQueryResponse<String> queryTransactionRwSet(@PathVariable("id") String txid) {
		return fabricService.queryTransactionRwSet(txid);
	}

	/**
	 * Query histories fabric query response.
	 *
	 * @param type the type
	 * @param key  the key
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	@GetMapping("/query/history")
	@ResponseBody
	public FabricQueryResponse<List<FabricHistory>> queryHistories(String type, String key) {
		return fabricService.queryHistory(type, key);
	}
}
