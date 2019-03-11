package com.gamelogservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamelogserviceApplicationTests {

	@Test
	public void testAddLogManageService() {
		GameLogManageEntity gameLogManageEntity=new GameLogManageEntity();
		gameLogManageEntity.setLogservicename("newtest");
		WebClient webClient = WebClient.create("http://localhost:8080");
		Mono<Void> testMono=webClient.post().uri("/logManage")
 		.contentType(MediaType.APPLICATION_STREAM_JSON) //声明请求体的数据格式为application/stream+json;
 		.syncBody(gameLogManageEntity)//body方法设置请求体的数据
//        .body(gameLogManageEntity, GameLogManageEntity.class) //body方法设置请求体的数据
        .retrieve()
        .bodyToMono(Void.class);
		//blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用
		testMono.block();
	}

}
