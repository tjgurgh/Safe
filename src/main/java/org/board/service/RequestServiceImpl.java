package org.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.board.domain.ApplicationVO;
import org.board.domain.UserVO;
import org.board.mapper.RequestMapper;
import org.board.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;

@Service
public class RequestServiceImpl implements RequestService{

	@Setter(onMethod_=@Autowired)
	private RequestMapper requestMapper;
	
	@Override
	public List<ApplicationVO> getList_R(){
		return requestMapper.getList_R();
	}
	
	@Override
	public List<ApplicationVO> getList_K(){
		return requestMapper.getList_K();
	}
	
	@Override
	public int approvalMember(UserVO user) {
		return requestMapper.approvalMember(user);
	}

	@Override
	public int denyApprovalMember(String mem_id) {
		return requestMapper.denyApprovalMember(mem_id);
	}
	
	@Override
	public List<UserVO> getList() {
		return requestMapper.getList();
	}

	@Override
	public int approvalReceipt(ApplicationVO app) {
		return requestMapper.approvalReceipt(app);
	}

	@Override
	public int denyApprovalReceipt(ApplicationVO app) {
		return requestMapper.denyApprovalReceipt(app);
	}

	@Override
	public int approvalKeep(ApplicationVO app) {
		return requestMapper.approvalKeep(app);
	}

	@Override
	public int denyApprovalKeep(ApplicationVO app) {
		return requestMapper.denyApprovalKeep(app);
	}


}
