package com.arit.adserve.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import com.arit.adserve.entity.Item;
import com.arit.adserve.providers.ebay.EBayFindRequest;
import com.arit.adserve.providers.ebay.RequestState;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DroolsTest {

	private KieSession kSession;
	private StatelessKieSession kStatelessSession;
	 
    @Before
    public void setup() throws IOException {        
        kSession = new DroolsConfig().getKieSession();
        kStatelessSession = new DroolsConfig().getKieStatlessSession();
    }
 
    @Test
    public void testEbayItem(){
        Item item = new Item();
        item.setPrice(7000);
        item.setCondition("Used");
        kSession.insert(item); 
        
        EBayFindRequest fr = new EBayFindRequest();
        fr.setState(RequestState.RETRIEVE_ITEMS);
        kSession.insert(fr);
        kSession.fireAllRules(); 
        assertTrue(item.isProcess());
    }
    
    @Test
	public void testEBayRequest() throws Exception {
    	 kSession = new DroolsConfig().getKieSession();
    	  EBayFindRequest fr = new EBayFindRequest();
      	  kSession.getAgenda().getAgendaGroup("ebayRequest").setFocus(); //focus on ebay request rules
          // initial state
    	  fr.setItemsTotal(0);
    	  fr.setItemsMaxRequired(200);
    	  fr.setSearchWords("searchWords_0");
    	  fr.setState(null);
          kSession.insert(fr);
          log.info("fact count {}", kSession.getFactCount());
          kSession.fireAllRules(); 
          assertEquals(RequestState.RETRIEVE_ITEMS, fr.getState());
          assertEquals(1, fr.getPageNumber());
          log.info("fact count {}", kSession.getFactCount());
          kSession.dispose();
          // after processing the first response
          fr.setItemsTotalInRequest(200);
          fr.setItemsPerPage(100);
          fr.setPagesTotal(2);
          fr.setItemsTotal(100);
          kSession = new DroolsConfig().getKieSession();
          kSession.getAgenda().getAgendaGroup("ebayRequest").setFocus();
          kSession.insert(fr);
          log.info("fact count {}", kSession.getFactCount());
          kSession.fireAllRules(); 
          log.info("fact count {}", kSession.getFactCount());
          assertEquals(RequestState.RETRIEVE_ITEMS, fr.getState());
          assertEquals(2, fr.getPageNumber());
          kSession.dispose();
          
          kSession = new DroolsConfig().getKieSession();
          kSession.getAgenda().getAgendaGroup("ebayRequest").setFocus();
          kSession.insert(fr);
	}

}
