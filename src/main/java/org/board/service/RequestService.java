package org.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.board.domain.ApplicationVO;
import org.board.domain.UserVO;

public interface RequestService {
	//수령 신청
	public int approvalReceipt(ApplicationVO app);
	//수령 거절
	public int denyApprovalReceipt(ApplicationVO app);

	//보관 신청
	public int approvalKeep(ApplicationVO app);
	//보관 거절
	public int denyApprovalKeep(ApplicationVO app);
	//회원 승인
	public int approvalMember(UserVO user);
	//회원 거절
	public int denyApprovalMember(String mem_id);

	// 회원 목록
	public List<UserVO> getList();

	public List<ApplicationVO> getList_R();

	public List<ApplicationVO> getList_K();
}
