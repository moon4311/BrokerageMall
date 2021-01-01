package injnsobang.com.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import injnsobang.com.service.ComCacheService;
import injnsobang.com.vo.ComIndvlzVO;

/**
 * @Class Name : ComCacheServiceImpl.java
 * @Description : 캐쉬 ServiceImpl
 * @Modification Information
 * 
 * @author
 * @since 2020. 07.20
 * @version 1.0
 * @see Copyright (C) by  All right reserved.
 */
@Service("comCacheService")
public class ComCacheServiceImpl extends AbstractServiceImpl implements ComCacheService, ApplicationListener<ContextRefreshedEvent> {
	// ////////////////////// Resource 선언 영역 ///////////////////////////////////////////////////////////////////
	@Resource(name = "comIndvlzDao")
	private ComIndvlzDao comIndvlzDao;

	@Resource(name = "comEtcDao")
	private ComEtcDao comEtcDao;
	// ////////////////////// Resource 선언 영역 끝 /////////////////////////////////////////////////////////////////

	// //////////////////////서비스 메소드 선언 영역 ///////////////////////////////////////////////////////////////////
	//최초 로딩.
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent ) {
		if(!isExistCache(ENUM_CACHE_KEY.INDVDLZ))reloadIndvlzList();
	}

	/**
	 * 개별코드 리로드
	 * @param codeParent 상위 코드
	 * @return
	 */
	public void reloadIndvlzList(){
		ComIndvlzVO searchVO = new ComIndvlzVO();
		searchVO.setRecordCountPerPage(0);
		setCache(ENUM_CACHE_KEY.INDVDLZ,comIndvlzDao.selectComIndvlzList(searchVO));
	}
	
	/**
	 * 개별코드 전체 가져오기
	 * @param codeParent 상위 코드
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComIndvlzVO> getAllIndvlzList(){
		if(!isExistCache(ENUM_CACHE_KEY.INDVDLZ))reloadIndvlzList();
		return (List<ComIndvlzVO>)getCache(ENUM_CACHE_KEY.INDVDLZ);
	}

	// //////////////////////서비스 메소드 선언 영역 끝 ///////////////////////////////////////////////////////////////////
//////////////////캐시 데이터 관련  ///////////////////////////////////////////////////////
	//singleton형식의 캐시 데이터 공간
	private static HashMap<ENUM_CACHE_KEY, Object> data;
	public HashMap<ENUM_CACHE_KEY, Object> getData(){
		if(data == null)data = new HashMap<ENUM_CACHE_KEY, Object>();
		return data;
	}
	/**
	* data에 값이 존재하는지 체크
	* @param key 캐시에 사용될 키값
	* @return
	*/
	private boolean isExistCache(ENUM_CACHE_KEY key){
		return (getData().containsKey(key));
	}
	/**
	* 캐시에 저장되어 있는 데이터 가져오기
	* @param key
	* @return
	*/
	public Object getCache(ENUM_CACHE_KEY key){
		return getData().get(key);
	}
	/**
	* 캐시에 데이터 저장하기
	* @param key
	* @return
	*/
	private Object setCache(ENUM_CACHE_KEY key,Object obj){
		return getData().put(key,obj);
	}
	
	/**
	* 캐시 삭제.
	*/
	public void clearAllCache(){
		data = new HashMap<ENUM_CACHE_KEY, Object>();
	}

//////////////////캐시 데이터 관련  종료 ///////////////////////////////////////////////////////
}
