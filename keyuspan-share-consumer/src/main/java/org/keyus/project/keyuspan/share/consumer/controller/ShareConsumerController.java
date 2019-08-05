package org.keyus.project.keyuspan.share.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.share.ShareClientService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keyus
 * @create 2019-08-05  下午2:41
 */
@RestController
@AllArgsConstructor
public class ShareConsumerController {

    private final ShareClientService shareClientService;


}
