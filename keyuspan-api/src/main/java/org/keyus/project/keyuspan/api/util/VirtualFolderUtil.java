package org.keyus.project.keyuspan.api.util;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;

import java.time.LocalDate;

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

    public static VirtualFolder createNewVirtualFolder (Long memberId, Long fatherFolderId, String folderName) {
        VirtualFolder newVirtualFolder = new VirtualFolder();
        newVirtualFolder.setMemberId(memberId);
        newVirtualFolder.setFatherFolderId(fatherFolderId);
        newVirtualFolder.setVirtualFolderName(folderName);
        newVirtualFolder.setUpdateDate(LocalDate.now());
        return newVirtualFolder;
    }

    public static VirtualFolder createMainVirtualFolder (Long memberId) {
        VirtualFolder virtualFolder = new VirtualFolder();
        virtualFolder.setMemberId(memberId);
        virtualFolder.setFatherFolderId(null);
        virtualFolder.setDeleted(false);
        return virtualFolder;
    }
}
