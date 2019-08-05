package org.keyus.project.keyuspan.share.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.share.provider.service.ShareRecordService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keyus
 * @create 2019-08-05  下午2:36
 */
@RestController
@AllArgsConstructor
public class ShareProviderController {

    private final ShareRecordService shareRecordService;


}
