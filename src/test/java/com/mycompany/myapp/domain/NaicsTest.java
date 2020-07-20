package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class NaicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naics.class);
        Naics naics1 = new Naics();
        naics1.setId(1L);
        Naics naics2 = new Naics();
        naics2.setId(naics1.getId());
        assertThat(naics1).isEqualTo(naics2);
        naics2.setId(2L);
        assertThat(naics1).isNotEqualTo(naics2);
        naics1.setId(null);
        assertThat(naics1).isNotEqualTo(naics2);
    }
}
