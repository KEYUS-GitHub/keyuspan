package org.keyus.project.keyuspan.api.lcn.service;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author keyus
 * @create 2019-08-14  下午2:35
 */
@NoArgsConstructor
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {

    @Value("${tm.manager.url}")
    private String url;

    @Override
    public String getTxUrl() {
        System.out.println("load tm.manager.url ");
        return url;
    }
}
