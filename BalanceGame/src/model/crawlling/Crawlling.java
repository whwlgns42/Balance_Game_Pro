package model.crawlling;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import model.question.QuestionDTO;

public class Crawlling {

	public ArrayList<QuestionDTO> crawling() {
		//
		String url = "https://zico8282.tistory.com/entry/%EC%9B%B9-%ED%81%AC%EB%A1%A4%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0";

		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 문제 랜덤으로 데이터 가져오기
		Elements question = doc.select(".tt_article_useless_p_margin span:contains(#)");
		
		int i = 0;
		ArrayList<QuestionDTO> datas = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;

		for (i = 0; i < question.size(); i++) {
			questionDTO = new QuestionDTO();
			String problum = question.get(i).text();
			String resultProb = problum.replaceAll("#", "");
			questionDTO.setTitle(resultProb);
			datas.add(questionDTO);

		}

		Elements answer = doc.select(".tt_article_useless_p_margin p:contains(^)");
		i = 0;
		for (int j = 0; j < answer.size(); j += 2) {
			String problumA = answer.get(j).text();
			String resultansA = problumA.replaceAll("\\^", "");

			String problumB = answer.get(j + 1).text();
			String resultansB = problumB.replaceAll("\\^", "");
			datas.get(i).setContent_A(resultansA);
			datas.get(i).setContent_B(resultansB);
			i++;
		}
		return datas;
	}
}
