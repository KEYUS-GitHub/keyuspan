package org.keyus.project.keyuspan.api.client.service.member;

import feign.hystrix.FallbackFactory;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-29  下午2:26
 */
@Component
public class MemberClientServiceFallbackFactory implements FallbackFactory<MemberClientService> {

    @Override
    public MemberClientService create(Throwable throwable) {
        return new MemberClientService() {
            @Override
            public ServerResponse<List<Member>> getMembers() {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<Member> saveMember(Member member) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<Member> findOne(Member member) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<Long>> getMemberIdList() {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_NOT_EXIST.getMessage());
            }
        };
    }
}
