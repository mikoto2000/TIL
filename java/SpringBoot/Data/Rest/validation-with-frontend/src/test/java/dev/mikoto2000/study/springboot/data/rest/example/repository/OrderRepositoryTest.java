package dev.mikoto2000.study.springboot.data.rest.example.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderRepositoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/dev/mikoto2000/study/springboot/data/rest/example/repository/OrderRepositoryTestTestCreateOrder.sql")
    public void testSortOrderName() throws Exception {
        this.mockMvc.perform(get("/orders?sort=orderName"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$._embedded.orders[0].orderName").value("oa"))
                .andExpect(jsonPath("$._embedded.orders[0].orderName").value("oa"))
                .andExpect(jsonPath("$._embedded.orders[1].orderName").value("ob"))
                .andExpect(jsonPath("$._embedded.orders[2].orderName").value("oc"))
                .andExpect(jsonPath("$._embedded.orders[3].orderName").value("od"));
    }

    @Test
    @Sql("/dev/mikoto2000/study/springboot/data/rest/example/repository/OrderRepositoryTestTestCreateOrder.sql")
    public void testSortId() throws Exception {
        this.mockMvc.perform(get("/orders?sort=id"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$._embedded.orders[0].orderName").value("od"))
                .andExpect(jsonPath("$._embedded.orders[1].orderName").value("oc"))
                .andExpect(jsonPath("$._embedded.orders[2].orderName").value("ob"))
                .andExpect(jsonPath("$._embedded.orders[3].orderName").value("oa"));
    }
}
