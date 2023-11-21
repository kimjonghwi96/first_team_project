package com.teamb.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.teamb.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.teamb.config.RootConfig.class} )
@Log4j
public class BoardServiceTest {

	@Setter(onMethod_ = {@Autowired})
	private BoardService service;
	private WebtoonService w_service;
	
	@Test
	public void testMain() {
		log.info(w_service);
		String WTJson = 
				
				"[{\"_id\": \"638250f70d980db259c73963\",\"webtoonId\": 3000050866481,\"title\": \"나 혼자만 레벨업\",\"author\": \"현군,장성락(REDICE STUDIO),추공\",\"url\": \"https://page.kakao.com/content/50866481\",	\"img\": \"//dn-img-page.kakao.com/download/resource?kid=xlYgE/hzMT3A4qrx/EFeMF4WPAB6G7m7ftD2dU0&filename=th3\",\"service\": \"kakaoPage\",\"updateDays\": [\"thu\"],\"fanCount\": 39000,\"searchKeyword\": \"나혼자만레벨업현군장성락redicestudio추공\",\"additional\": {\"new\": false,\"rest\": false,\"up\": false,\"adult\": false,\"singularityList\": []}},{\"_id\": \"638250d70d980db259c72c72\",\"webtoonId\": 2000000002320,\"title\": \"나 혼자만 레벨업\",\"author\": \"현군,장성락(REDICE STUDIO)\",\"url\": \"https://webtoon.kakao.com/content/나-혼자만-레벨업/2320\",\"img\": \"https://kr-a.kakaopagecdn.com/P/C/2320/c1/2x/88ac0b74-c10e-4cd9-8cf8-8fae2514ad4c.png\",\"service\": \"kakao\",\"updateDays\": [\"thu\"],\"fanCount\": 365,\"searchKeyword\": \"나혼자만레벨업현군장성락redicestudio\",\"additional\": {\"new\": false,\"rest\": false,\"up\": true,\"adult\": false,\"singularityList\": [\"waitFree\"]}}]";
				
		w_service.uploadWebtoonByJson(WTJson);
	}
	
//	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}
	
//	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setUser_id("testuser0013");
		board.setTitle("서비스 테스트 제목");
		board.setBoard_content("서비스 테스트 내용");
		board.setBoard_regdate(new Date());
		board.setBoard_w_updatedate(new Date());
		
		service.register(board);
		
		log.info("새로 작성된 게시물의 번호: " + board.getBoard_num());
	}
	
//	@Test
	public void testGetList() {
		service.getList().forEach(board -> log.info(board));
	}
	
//	@Test
	public void testGet() {
		//L앞에 가져올 게시판 글 번호
		log.info(service.get(18L));
	}
	
//	@Test
	public void testDelete() {
		// 게시글 번호의 존재여부 확인후 테스트,L앞에 가져올 게시판 글 번호
		log.info("삭제 결과 : " + service.remove(11L));
	}
	
//	@Test
	public void testUpdate() {
//		L앞에 가져올 게시판 글 번호
		BoardVO board = service.get(12L);
		if (board == null) {
			return;
		}
		board.setTitle("제목 수정합니다.");
		log.info("수정 결과 입니다." + service.modify(board));
	}
	
}
