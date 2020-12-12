package com.bocsh.base.web;

import com.bocsh.base.domain.RestPageImpl;
import com.bocsh.base.dto.ServeFindDto;
import com.bocsh.base.dto.ServeQueryDto;
import com.bocsh.base.dto.ServeSaveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
class ServeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<RestPageImpl<ServeFindDto>> serveList;

    @Autowired
    private JacksonTester<ServeQueryDto> serveQuery;

    @Autowired
    private JacksonTester<ServeSaveDto> serveSave;

    @Autowired
    private WebTestClient webClient;

    /* 获取全量服务列表 */
    @Test
    void getServiceAll() throws Exception {

        mvc.perform(post("/services/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}")
                .accept("application/json; charset=utf-8"))
                .andExpect(status().isOk());
    }

    /* 根据指定服务id查询服务列表 */
    @Test
    void getServiceAllById() throws Exception {

        ServeQueryDto query = new ServeQueryDto();
        query.setServiceId("bocsh-service-base");

        MvcResult mvcResult =  mvc.perform(post("/services/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.serveQuery.write(query).getJson())
                .accept("application/json; charset=utf-8")).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        RestPageImpl<ServeFindDto> list = this.serveList.parseObject(content);

        assertThat(list.getContent().get(0).getServiceId()).isEqualTo("bocsh-service-base");

    }

    /* 根据服务名称模糊查询微服务列表 */
    @Test
    void getServiceAllByName() throws Exception {

        ServeQueryDto query = new ServeQueryDto();
        query.setServiceName("航运");

        MvcResult mvcResult =  mvc.perform(post("/services/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.serveQuery.write(query).getJson())
                .accept("application/json; charset=utf-8")).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        RestPageImpl<ServeFindDto> list = this.serveList.parseObject(content);

        assertThat(list.getContent().get(0).getServiceId()).isEqualTo("bocsh-service-shpp");

    }

    /* 新增微服务定义，服务编号超长，应返回400 */
    @Test
    void addServiceWithWrongServiceId() throws Exception {

        ServeSaveDto serve = new ServeSaveDto();
        serve.setBackendDev("张三");
        serve.setFrontDev("李四");
        serve.setPm("王五");
        serve.setServiceDesc("测试服务");
        serve.setServiceId("bocsh-service-test2");
        serve.setServiceName("测试微服务");

        webClient.post().uri("/services")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(serve), ServeSaveDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

    }

    /* 新增微服务定义，服务名称超长，应返回400 */
    @Test
    void addServiceWithWrongServiceName() throws Exception {

        ServeSaveDto serve = new ServeSaveDto();
        serve.setBackendDev("张三");
        serve.setFrontDev("李四");
        serve.setPm("王五");
        serve.setServiceDesc("测试服务");
        serve.setServiceId("bocsh-service-test2");
        serve.setServiceName("测试微服务111111111111111111111111111111");

        webClient.post().uri("/services")
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    /* 新增微服务定义，数据符合要求，应返回201 */
    @Test
    void addService() throws Exception {

        ServeSaveDto serve = new ServeSaveDto();
        serve.setBackendDev("张三");
        serve.setFrontDev("李四");
        serve.setPm("王五");
        serve.setServiceDesc("测试服务");
        serve.setServiceId("bocsh-service-test1");
        serve.setServiceName("测试微服务");

        MvcResult mvcResult =  mvc.perform(post("/services")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.serveSave.write(serve).getJson())
                .accept("application/json; charset=utf-8")).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);

    }


}