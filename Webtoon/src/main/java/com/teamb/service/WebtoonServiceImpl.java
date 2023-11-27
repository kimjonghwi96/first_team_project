package com.teamb.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamb.domain.WTUploadAdditionalVO;
import com.teamb.domain.WTUploadVO;
import com.teamb.domain.WebtoonSearchVO2;
import com.teamb.domain.WebtoonVO;
import com.teamb.mapper.WebtoonMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class WebtoonServiceImpl implements WebtoonService {

	@Setter
	private WebtoonMapper w_mapper;

	@Override
	public List<WebtoonVO> getWebtoonList() {
		return w_mapper.getWebtoonList();
	}

	@Override
	public List<WebtoonVO> WebtoonSearch() {
		return w_mapper.WebtoonSearch(null);
	}

	@Override
	public Object getSearchGenreButton() {
		return w_mapper.getSearchGenreButton();
	}

	@Override
	public Object getSearchPlatformButton() {
		return w_mapper.getSearchPlatformButton();
	}

	@Override
	public Object getSearchPaymentButton() {
		return w_mapper.getSearchPaymentButton();
	}

	@Override
	public Object getSearchWeekdayButton() {
		return w_mapper.getSearchWeekdayButton();
	}

	// 검색기능
	@Override
	public Object getWebtoonSearch(WebtoonSearchVO2 webtoonSearch) {

		ArrayList<Long> genre_code = new ArrayList<>();
		ArrayList<Long> platform_code = new ArrayList<>();
		ArrayList<Long> payment_code = new ArrayList<>();
		ArrayList<Long> weekday_code = new ArrayList<>();

		// genre_code
		if (webtoonSearch.getGenre_code().size() != 0) {
			for (int i = 0; i < webtoonSearch.getGenre_code().size(); i++) {
				genre_code.add(webtoonSearch.getGenre_code().get(i));
			}
			for (int i = webtoonSearch.getGenre_code().size(); i < 14; i++) {
				genre_code.add(webtoonSearch.getGenre_code().get(0));
			}

		}

		// platform_code
		if (webtoonSearch.getPlatform_code().size() == 0) {
			platform_code.add(1L);
			platform_code.add(2L);
			platform_code.add(3L);
		} else {

			for (int i = 0; i < webtoonSearch.getPlatform_code().size(); i++) {
				platform_code.add(webtoonSearch.getPlatform_code().get(i));
			}
			for (int i = webtoonSearch.getPlatform_code().size(); i < 3; i++) {
				platform_code.add(webtoonSearch.getPlatform_code().get(0));
			}
		}

		// payment_code
		if (webtoonSearch.getPayment_code().size() == 0) {
			payment_code.add(1L);
			payment_code.add(2L);
			payment_code.add(3L);
		} else {

			for (int i = 0; i < webtoonSearch.getPayment_code().size(); i++) {
				payment_code.add(webtoonSearch.getPayment_code().get(i));
			}
			for (int i = webtoonSearch.getPayment_code().size(); i < 3; i++) {
				payment_code.add(webtoonSearch.getPayment_code().get(0));
			}
		}

		// weekday_code
		if (webtoonSearch.getWeekday_code().size() == 0) {
			weekday_code.add(1L);
			weekday_code.add(2L);
			weekday_code.add(3L);
			weekday_code.add(4L);
			weekday_code.add(5L);
			weekday_code.add(6L);
			weekday_code.add(7L);
			weekday_code.add(8L);
			weekday_code.add(9L);
		} else {
			for (int i = 0; i < webtoonSearch.getWeekday_code().size(); i++) {
				weekday_code.add(webtoonSearch.getWeekday_code().get(i));
			}
			for (int i = webtoonSearch.getWeekday_code().size(); i < 9; i++) {
				weekday_code.add(webtoonSearch.getWeekday_code().get(0));
			}
		}

		String author_name = "%" + webtoonSearch.getAuthor_name() + "%";

		String webtoon_title = "%" + webtoonSearch.getWebtoon_title() + "%";

		WebtoonSearchVO2 webtoonSearch1 = new WebtoonSearchVO2();
		webtoonSearch1.setGenre_code(genre_code);
		webtoonSearch1.setPlatform_code(platform_code);
		webtoonSearch1.setPayment_code(payment_code);
		webtoonSearch1.setWeekday_code(weekday_code);
		webtoonSearch1.setWebtoon_title(webtoon_title);
		webtoonSearch1.setAuthor_name(author_name);

		if (webtoonSearch.getGenre_code().size() != 0) {
			return w_mapper.WebtoonSearch(webtoonSearch1);
		} else {
			return w_mapper.WebtoonSearch2(webtoonSearch1);
		}
	}

	@Override
	public WebtoonVO getWebtoonDetail(Long webtoon_id) {
		return w_mapper.getWebtoonDetail(webtoon_id);
	}

	// 세부사항
	@Override
	public List<WebtoonVO> getWebtoonPlatform(Long webtoon_id) {
		return w_mapper.getWebtoonPlatform(webtoon_id);
	}

	@Override
	public List<WebtoonVO> getWebtoonGenre(Long webtoon_id) {
		return w_mapper.getWebtoonGenre(webtoon_id);
	}

	@Override
	public List<WebtoonVO> getWebtoonWeekday(Long webtoon_id, Long platform_code) {
		return w_mapper.getWebtoonWeekday(webtoon_id, platform_code);
	}

	@Override
	public List<WebtoonVO> getWebtoonAuthor(Long webtoon_id, Long platform_code) {
		return w_mapper.getWebtoonAuthor(webtoon_id, platform_code);
	}

	@Override
	public WebtoonVO getPlatformChar(Long webtoon_id, Long platform_code) {
		return w_mapper.getPlatformChar(webtoon_id, platform_code);
	}

	@Override
	public void hitUp(Long webtoon_id, String user_id) {

		WebtoonVO selectView_count = w_mapper.selectView_count(webtoon_id, user_id);

		if (selectView_count == null) {
			w_mapper.view_count_new(webtoon_id, user_id);

		} else {
			w_mapper.view_count_up(webtoon_id, user_id);
		}
	}

	@Override
	public void updateRanking(Long webtoon_id) {
		WebtoonVO selectRanking = w_mapper.selectRanking(webtoon_id);

		if (selectRanking == null) {
			w_mapper.ranking_new(webtoon_id);
			w_mapper.updatePopularity(webtoon_id);
			w_mapper.updateRanking();
			// 트랜젝션
		} else {
			w_mapper.updatePopularity(webtoon_id);
			w_mapper.updateRanking();
		}
	}

	@Override
	public Long isLiked(String user_id, Long webtoon_id) {
		if (w_mapper.getlike(user_id, webtoon_id) == null) {
			return 0L;
		} else {
			return 1L;
		}
	}

	@Override
	public Long isFav(String user_id, Long webtoon_id) {
		if (w_mapper.getFav(user_id, webtoon_id) == null) {
			return 0L;
		} else {
			return 1L;
		}
	}

	@Override
	public Long insertOrDeleteLike(Long isLiked, String user_id, Long webtoon_id) {
		if (isLiked == 0L) {
			w_mapper.insertLike(user_id, webtoon_id);
			isLiked = 1L;
		} else {
			w_mapper.deleteLike(user_id, webtoon_id);
		}
		return isLiked;
	}

	@Override
	public Long insertOrDeleteFav(Long isFav, String user_id, Long webtoon_id) {
		if (isFav == 0L) {
			w_mapper.insertFav(user_id, webtoon_id);
			isFav = 1L;
		} else {
			w_mapper.deleteFav(user_id, webtoon_id);
		}
		return isFav;
	}

	@Override
	public List<WebtoonVO> sameAuthorWT(Long webtoon_id) {
		// 웹툰의 작가를 모두 불러와서 그 작가들의 작품을 가져옴
		return w_mapper.sameAuthorWT(webtoon_id);
	}

	@Override
	public Long isCommentExist(String user_id, Long webtoon_id) {
		if (w_mapper.isCommentExist(user_id, webtoon_id) == null) {
			return 0L;
		} else {
			return 1L;
		}
	}

	@Override
	public void insertComment(String user_id, Long webtoon_id, String newComment) {
		w_mapper.insertComment(user_id, webtoon_id, newComment);

	}

	@Override
	public void uploadWebtoonByJson(String WTJson) {

		JSONParser parser = new JSONParser();
		ObjectMapper objectMapper = new ObjectMapper();
		JSONArray jsonWTArr = null;

		try {
			JsonNode jsonNode = objectMapper.readTree(WTJson);

			if (jsonNode.isObject()) {
				System.out.println("이것은 JSON 객체입니다.");

				JSONObject jsonWTObj = (JSONObject) parser.parse(WTJson);

				if ((JSONArray) jsonWTObj.get("webtoons") == null) {
					System.out.println("JSON 객체 웹툰 하나짜리");
					String WTJson_toArr = "[" + WTJson + "]";
					jsonWTArr = (JSONArray) parser.parse(WTJson_toArr);

				} else {
					System.out.println("JSON 객체 웹툰 여러개");
					jsonWTArr = (JSONArray) jsonWTObj.get("webtoons");
				}

			} else if (jsonNode.isArray()) {
				System.out.println("이것은 JSON 배열입니다.");
				jsonWTArr = (JSONArray) parser.parse(WTJson);

			} else {
				System.out.println("이것은 유효한 JSON 형식이 아닙니다.");
			}
		} catch (Exception e) {
			System.out.println("JSON 파싱 중 오류가 발생했습니다: " + e.getMessage());
		}

		WTUploadAdditionalVO uploadAdditionalVO = new WTUploadAdditionalVO();
		ArrayList<WTUploadVO> uploadList = new ArrayList<>();

		for (Object element : jsonWTArr) {

			WTUploadVO upload = new WTUploadVO();

			upload.setWebtoonId((Long) ((JSONObject) element).get("webtoonId"));// 같은 웹툰인데 다른 플랫폼인 것 합쳐야함(같은
																				// 제목이어도 다른 웹툰일 가능성 있음)
			upload.setTitle(((String) ((JSONObject) element).get("title")).trim());

			// 문자열
			String data = (((String) ((JSONObject) element).get("author")).trim().replaceAll("^,|,$", "")).trim();

			System.out.println("-----------------------------------------" + data);

			// 괄호 안의 콤마를 제외하고 밖의 콤마만으로 문자열을 분리
			Pattern pattern = Pattern.compile("\\([^)]*\\)|,");
			Matcher matcher = pattern.matcher(data);
			HashSet<String> authorSet = new HashSet<>();

			int start = 0;
			while (matcher.find()) {
				String match = matcher.group();
				if (match.equals(",") && data.substring(start, matcher.start()) != "") {
					authorSet.add(data.substring(start, matcher.start()).trim());
					start = matcher.end();
				}
			}

			authorSet.add(data.substring(start).trim());
			ArrayList<String> author = new ArrayList<>(authorSet);

			upload.setAuthor(author);// 여러명이 묶여있는 json을 나눠야 함/완료
			upload.setUrl((String) ((JSONObject) element).get("url"));
			upload.setImg((String) ((JSONObject) element).get("img"));

			String service = (String) ((JSONObject) element).get("service");

			upload.setService(service);// 영어로 된 json을 우리 테이블(한글)에 맞춰서 변환해야 함

			ArrayList<String> udd = (ArrayList<String>) ((JSONObject) element).get("updateDays");

			upload.setUpdateDays(udd);// 영어로 된 json을 우리 테이블(한글)에 맞춰서 변환해야 함/완료

			uploadAdditionalVO
					.setW_new1((Boolean) ((((JSONObject) ((JSONObject) element).get("additional")).get("new"))) ? "true"
							: "false"); // Boolean형
			uploadAdditionalVO
					.setRest1((Boolean) ((((JSONObject) ((JSONObject) element).get("additional")).get("rest"))) ? "true"
							: "false"); // Boolean형
			uploadAdditionalVO.setUp1(
					(Boolean) ((((JSONObject) ((JSONObject) element).get("additional")).get("up"))) ? "true" : "false"); // Boolean형
			uploadAdditionalVO.setAdult1(
					(Boolean) ((((JSONObject) ((JSONObject) element).get("additional")).get("adult"))) ? "true"
							: "false"); // Boolean형

			upload.setAdditional(uploadAdditionalVO);
			uploadList.add(upload);
		}


		// 작품 등록 및 수정
		for (int i = 0; i < uploadList.size(); i++) {

			Long webtoon_id = uploadList.get(i).getWebtoonId();
			String webtoon_title = uploadList.get(i).getTitle();
			ArrayList<String> author_name = uploadList.get(i).getAuthor();
			String url = uploadList.get(i).getUrl();
			String thumbnail = uploadList.get(i).getImg();
			String platform_name = uploadList.get(i).getService();
			ArrayList<String> weekday_day = uploadList.get(i).getUpdateDays();
			WTUploadAdditionalVO additional = uploadList.get(i).getAdditional();

			// author 있는지 보고 추가 추가
			for (String element : author_name) {
				if (w_mapper.selectAuthor(element) == null) {
					w_mapper.insertAuthor(element);
				}
			}

			// payment 보류

			// platform 추가
			if (w_mapper.selectPlatform(platform_name) == null) {
				w_mapper.insertPlatform(platform_name);
			}

			// weekday 추가
			for (String element : weekday_day) {
				if (w_mapper.selectWeekday(element) == null) {
					w_mapper.insertWeekday(element);
				}
			}

			// 웹툰아이디가 기존에 존재할 때
			if (w_mapper.selectWebtoonId(webtoon_id) != null) {

				// additional 수정
				w_mapper.updateAdditional(webtoon_id, platform_name, additional);
			}

			// 업로드하는 작품명의 웹툰 아이디 리스트 추출
			// 추출된 웹툰 아이디 리스트의 하나의 플랫폼 리스트 추출
			// 플랫폼 리스트와 업로드 작품 플랫폼 비교

			// 윁툰아이디가 없고, 같은 제목이 있을 때
			else if (!w_mapper.selectWebtoonIdByTitle(webtoon_title).isEmpty()) {

				// 업로드하는 웹툰의 플랫폼이 기존 같은 제목 웹툰이 가진 플랫폼에 속하는지
				// 기존 같은 제목 웹툰이 2개이상일 수 도(2중for문)

				Long webtoon_id1 = 0L;

				// 플랫폼이 같은게 있다면 webtoon_id1에 기존에 존재하는 webtoon_id가 담김(바뀜)

				List<WebtoonVO> WebtoonIdByTitle = w_mapper.selectWebtoonIdByTitle(webtoon_title);
				for (WebtoonVO element : WebtoonIdByTitle) {
					for (WebtoonVO element2 : w_mapper.getWebtoonPlatform(element.getWebtoon_id())) {
						if (platform_name.equals(element2.getPlatform_name())) {
							webtoon_id1 = element.getWebtoon_id();
						}
					}
				}

				// 웹툰아이디가 기존에 없고 같은 제목이 있지만 플랫폼이 같은게 없다-같은 웹툰(다른 플랫폼)
				if (webtoon_id1 == 0L) {
					System.out.println("1===============================================" + webtoon_title);

					webtoon_id = WebtoonIdByTitle.get(0).getWebtoon_id();

					// platformcharacter 테이블 insert
					w_mapper.insertPlatformcharacter(webtoon_id, platform_name, url, 2L);

					// additional 테이블 update
					w_mapper.updateAdditional(webtoon_id, platform_name, additional);

					// wt_aut 테이블 insert
					for (String element : author_name) {
						w_mapper.insertWt_aut(webtoon_id, platform_name, element);

					}

					// wt_wd_pl 테이블 insert
					for (String element : weekday_day) {
						w_mapper.insertWt_wd_pl(webtoon_id, element, platform_name);
					}
				}
				// 웹툰아이디가 기존에 없고 같은 제목이 있고 플랫폼이 같은게 있다
				// 같은 작가를 공유하면 같은 웹툰/ 아니면 별개의 웹툰
				else {
					System.out.println("2===============================================" + webtoon_title);

					if (w_mapper.similarAuthorWT(webtoon_title, author_name) != null) {
						if (w_mapper.similarAuthorWT(webtoon_title, author_name).getAuthor_count() > 0L) {
							webtoon_id = w_mapper.similarAuthorWT(webtoon_title, author_name).getWebtoon_id();
							w_mapper.updateAdditional(webtoon_id, platform_name, additional);
						} else {
							// webtoon 테이블 insert
							w_mapper.insertWebtoon(webtoon_id, webtoon_title, thumbnail);

							// platformcharacter 테이블 insert
							w_mapper.insertPlatformcharacter(webtoon_id, platform_name, url, 2L);

							// additional 테이블 update
							w_mapper.updateAdditional(webtoon_id, platform_name, additional);

							// webtoon_ranking 테이블 insert
							w_mapper.ranking_new(webtoon_id);

							// wt_gen 디폴트값(수정으로 직접 넣을 예정)
							w_mapper.insertWT_gen(webtoon_id, "기타");

							// wt_aut 테이블 insert
							for (String element : author_name) {
								w_mapper.insertWt_aut(webtoon_id, platform_name, element);
							}

							// wt_wd_pl 테이블 insert
							for (String element : weekday_day) {
								w_mapper.insertWt_wd_pl(webtoon_id, element, platform_name);
							}
						}
					} else {
						// webtoon 테이블 insert
						w_mapper.insertWebtoon(webtoon_id, webtoon_title, thumbnail);

						// platformcharacter 테이블 insert
						w_mapper.insertPlatformcharacter(webtoon_id, platform_name, url, 2L);

						// additional 테이블 update
						w_mapper.updateAdditional(webtoon_id, platform_name, additional);

						// webtoon_ranking 테이블 insert
						w_mapper.ranking_new(webtoon_id);

						// wt_gen 디폴트값(수정으로 직접 넣을 예정)
						w_mapper.insertWT_gen(webtoon_id, "기타");

						// wt_aut 테이블 insert
						for (String element : author_name) {
							w_mapper.insertWt_aut(webtoon_id, platform_name, element);
						}

						// wt_wd_pl 테이블 insert
						for (String element : weekday_day) {
							w_mapper.insertWt_wd_pl(webtoon_id, element, platform_name);
						}

					}

				}

			}
			// 웹툰아이디가 기존에 존재하지 않고 같은 제목도 없을 때
			else {

				// webtoon 테이블 insert
				w_mapper.insertWebtoon(webtoon_id, webtoon_title, thumbnail);

				// platformcharacter 테이블 insert
				w_mapper.insertPlatformcharacter(webtoon_id, platform_name, url, 2L);

				// additional 테이블 update
				w_mapper.updateAdditional(webtoon_id, platform_name, additional);

				// webtoon_ranking 테이블 insert
				w_mapper.ranking_new(webtoon_id);

				// wt_gen 디폴트값(수정으로 직접 넣을 예정)
				w_mapper.insertWT_gen(webtoon_id, "기타");

				// wt_aut 테이블 insert
				for (String element : author_name) {
					w_mapper.insertWt_aut(webtoon_id, platform_name, element);
				}

				// wt_wd_pl 테이블 insert
				for (String element : weekday_day) {
					w_mapper.insertWt_wd_pl(webtoon_id, element, platform_name);
				}
			}
		}

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 플랫폼 url가져오기
	@Override
	public List<WebtoonVO> plurl() {
		return w_mapper.plurl();
	}

	@Override
	public List<WebtoonVO> cmtList(Long webtoon_id) {
		return w_mapper.cmtList(webtoon_id);
	}

	// 즐겨찾기
	@Override
	public List<WebtoonVO> fav_getWebtoonList(WebtoonVO webtoon) {
		return w_mapper.fav_getWebtoonList(webtoon);
	}

	@Override
	public void wt_delete(Long webtoon_id) {
		w_mapper.wt_delete(webtoon_id);
	}

	@Override
	public List<WebtoonVO> SimilarWT(Long webtoon_id) {
		return w_mapper.SimilarWT(webtoon_id);
	}

}
