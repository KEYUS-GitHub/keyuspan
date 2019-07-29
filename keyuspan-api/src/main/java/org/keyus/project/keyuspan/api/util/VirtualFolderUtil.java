package org.keyus.project.keyuspan.api.util;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;

import java.util.Objects;

/**
 * @author keyus
 * @create 2019-07-28  下午4:13
 */
public class VirtualFolderUtil {

    public static boolean isBelongToThisMember (Member member, VirtualFolder virtualFolder) {
        // 保证member与virtualFolder都不为空
        if (member == null || virtualFolder == null)
            return false;
        return Objects.equals(member.getId(), virtualFolder.getMemberId());
    }
}