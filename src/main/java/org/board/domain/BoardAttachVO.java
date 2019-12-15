package org.board.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String uuid; //UUID
	private String uploadPath; //���� ���� ���ε� ���
	private String fileName; //�����̸�
	private boolean fileType; //�̹��� ���� ����
	
	private Long brd_id; //������ �Խù� ��ȣ
}
