package cn.precious.metal.services;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AndroidAPIConfig;
import cn.precious.metal.entity.EconomicCalenda;
import cn.precious.metal.entity.response.EconomicCalendaResponseContent;

public class EconnomicCalendaService extends AbstractKjsService {

	public EconnomicCalendaService(Context context) {
		super(context);
	}

	//获取华尔街的财经日历
	public List<EconomicCalenda> queryEconomicCalendaByDates(String startDate,
			String endDate) throws ServiceException{
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("start", URLEncoder.encode(startDate));
		headerMap.put("end", URLEncoder.encode(endDate));
		
		
		EconomicCalendaResponseContent resp = null;
		try {
			resp = (EconomicCalendaResponseContent) callGetApi(
					AndroidAPIConfig.HUAERJIE_ECONOMIC_CANLENDA, headerMap,
					EconomicCalendaResponseContent.class);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		if (resp != null) {
			return resp.getResults() ;
		} else
			return null;

	}
}
