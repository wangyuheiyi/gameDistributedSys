package com.dc.testthymeleaf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.dc.testthymeleaf.bean.ResInfoBean;
import com.dc.testthymeleaf.entity.GameLogManageEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestthymeleafApplicationTests {

//	@Test
//	public void contextLoads() {
//		GameLogManageEntity gameLogManageEntity=new GameLogManageEntity();
//		gameLogManageEntity.setGamecode(2);
//		gameLogManageEntity.setLogservicename("测试4");
//		gameLogManageEntity.setGamename("新测试4");
//		gameLogManageEntity.setSendObjName("myobj");
//		gameLogManageEntity.setSendServicePackage("1");
//		gameLogManageEntity.setSendBeanPackage("2");
//		gameLogManageEntity.setChannelName("oooooooo");
//		WebClient webClient = WebClient.create("http://localhost:8091");
//		Mono<String> testMono=webClient.post().uri("/saveInfo")
// 		.contentType(MediaType.APPLICATION_STREAM_JSON) //声明请求体的数据格式为application/stream+json;
// 		.syncBody(gameLogManageEntity)//body方法设置请求体的数据
////        .body(gameLogManageEntity, GameLogManageEntity.class) //body方法设置请求体的数据
//        .retrieve() //获取返回结果
//        .bodyToMono(String.class);
//		//blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用
//		testMono.subscribe(System.out::println);
//		testMono.block();
//	}

	@Test
	public void contextLoads1() {
		WebClient webClient = WebClient.create("http://localhost:8091");
		webClient.get().uri("/findByGameCodeMongo/6")
//        .body(gameLogManageEntity, GameLogManageEntity.class) //body方法设置请求体的数据
        .retrieve() //获取返回结果
        .bodyToMono(ResInfoBean.class).doOnNext(res-> {
			System.out.println("===========================");
			System.out.println(res.getStatus());
			System.out.println(res.getResStr());
		}).block();
		//blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用
//		testMono.subscribe(res-> {
//			System.out.println("===========================");
//			System.out.println(res.getStatus());
//			System.out.println(res.getResStr());
//		});
//		testMono.block();
	}
}
