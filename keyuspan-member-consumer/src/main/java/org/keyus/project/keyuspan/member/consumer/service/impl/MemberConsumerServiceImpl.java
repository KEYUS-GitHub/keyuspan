package org.keyus.project.keyuspan.member.consumer.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.folder.FolderClientService;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.member.consumer.service.MemberConsumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author keyus
 * @create 2019-08-12  下午2:22
 */
@Service
@Transactional
@AllArgsConstructor
public class MemberConsumerServiceImpl implements MemberConsumerService {

    private final MemberClientService memberClientService;

    private final FolderClientService folderClientService;

    @Override
    public ServerResponse getMembers() {
        return memberClientService.getMembers();
    }

    @Override
    public ServerResponse register(Member member, String key, String capText) {
        // 验证码校验
        if (Objects.equals(key, capText)) {
            // 构建查询对象
            Member check = new Member();
            check.setUsername(member.getUsername());
            ServerResponse<Member> one = memberClientService.findOne(check);
            if (ServerResponse.isError(one)) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
            } else if (ServerResponse.isNullValue(one)) {
                ServerResponse<Member> response = memberClientService.saveMember(member);
                if (ServerResponse.isSuccess(response)) {
                    Member save = response.getData();
                    ServerResponse<VirtualFolder> serverResponse = folderClientService.createMainFolder(save.getId());
                    if (ServerResponse.isSuccess(serverResponse)) {
                        VirtualFolder virtualFolder = serverResponse.getData();
                        save.setMainFolderId(virtualFolder.getId());
                        return memberClientService.saveMember(save);
                    } else {
                        return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_REGISTER_FAIL.getMessage());
                    }
                } else {
                    return response;
                }
            } else {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.USERNAME_REPEAT.getMessage());
            }
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.CAPTCHA_CHECK_ERROR.getMessage());
        }
    }

    @Override
    public ServerResponse login(HttpSession session, Member member, String key) {

        // 获取并校验验证码的值
        String capText = (String) session.getAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_IMAGE.getName());
        if (!Objects.equals(key, capText)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.CAPTCHA_CHECK_ERROR.getMessage());
        }

        // 校验成功，则查找用户名与密码匹配的记录
        ServerResponse <Member> response = memberClientService.findOne(member);
        if (ServerResponse.isSuccess(response)) {
            // 用户名或密码错误，查无此记录，故而登录失败
            if (ServerResponse.isNullValue(response)) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.USERNAME_OR_PASSWORD_ERROR.getMessage());
            } else {
                // 登录成功，登录信息存入session当中
                session.setAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName(), response.getData());
                return response;
            }
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
        }
    }
}