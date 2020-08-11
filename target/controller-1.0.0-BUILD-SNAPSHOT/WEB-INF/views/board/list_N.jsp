<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../includes/nav.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">총 게시글 수 : ${pageMaker.total}개</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				게시판 목록
				<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
					<button id='regBtn' type="button" class="btn btn-xs pull-right">글쓰기</button>
				</sec:authorize>
				
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>작성일</th>
							<th>수정일</th>
						</tr>
					</thead>

					<!-- BRD_TYPE이 N일때 -> 공지사항 일때 만 출력해라 -->

					<c:forEach items="${list_N}" var="board">

						<tr>
							<td><c:out value="${board.brd_id }" /></td>
							<td><a class='move' href='<c:out value="${board.brd_id}"/>'>
									<c:out value="${board.brd_sub }" /> <b>[<c:out
											value="${board.cmt_cnt}" />]
								</b>
							</a></td>
							<td><c:out value="${board.brd_writer }" /></td>
							<td><c:out value="${board.brd_cnt }" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.brd_date }" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.brd_updateDate }" /></td>
						</tr>
					</c:forEach>
				</table>

				<div class="row">
					<div class="col-lg-12">
						<form id="searchForm" action="/board/list_N" method="get">
							<input type="hidden" name="pageNum"
								value='<c:out value="${pageMaker.cri.pageNum }"/>'> <input
								type="hidden" name="amount"
								value='<c:out value="${pageMaker.cri.amount }"/>'> <select
								name="type">
								<option value=""
									<c:out value="${pageMaker.cri.type == null? 'selected':'' }"/>>종류</option>
								<option value="T"
									<c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }"/>>제목</option>
								<option value="C"
									<c:out value="${pageMaker.cri.type eq 'C'?'selected':'' }"/>>내용</option>
								<option value="W"
									<c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }"/>>작성자</option>
								<option value="TC"
									<c:out value="${pageMaker.cri.type eq 'TC'?'selected':'' }"/>>제목
									or 내용</option>
								<option value="TW"
									<c:out value="${pageMaker.cri.type eq 'TW'?'selected':'' }"/>>제목
									or 작성자</option>
								<option value="TWC"
									<c:out value="${pageMaker.cri.type eq 'TWC'?'selected':'' }"/>>제목
									or 내용 or 작성자</option>
							</select> <input type="text" name="keyword"
								value='<c:out value="${pageMaker.cri.keyword }"/>'>
							<button class="btn btn-default">Search</button>
						</form>
					</div>
				</div>
				<div class='board-center' style="text-align: center;">
					<ul class="pagination" style="text-align: center;">
						<c:if test="${pageMaker.prev}">
							<li class="paginate_button previous"><a
								href="${pageMaker.startPage -1}">Previous</a></li>
						</c:if>

						<c:forEach var="num" begin="${pageMaker.startPage}"
							end="${pageMaker.endPage}">
							<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""}">
								<a href="${num}">${num}</a>
							</li>
						</c:forEach>

						<c:if test="${pageMaker.next}">
							<li class="paginate_button next"><a
								href="${pageMaker.endPage +1 }">Next</a></li>
						</c:if>
					</ul>
				</div>


				<form id='actionForm' action="/board/list_N" method="get">
					<input type="hidden" name="pageNum"
						value='<c:out value="${pageMaker.cri.pageNum }"/>'> <input
						type="hidden" name="amount"
						value='<c:out value="${pageMaker.cri.amount }"/>'> <input
						type="hidden" name="type"
						value='<c:out value="${pageMaker.cri.type }"/>'> <input
						type="hidden" name="keyword"
						value='<c:out value="${pageMaker.cri.keyword }"/>'>
				</form>
				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">게시글 작성</h4>
							</div>
							<div class="modal-body">처리가 완료되었습니다</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">확인</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
				<!-- /.modal -->
			</div>
		</div>
		<!-- /.panel-body -->
	</div>
	<!-- /.panel -->
</div>



<%@include file="../../includes/footer.jsp"%>

<script>
	$(document)
			.ready(
					function() {
						var result = '<c:out value = "${result}"/>';

						checkModal(result);

						history.replaceState({}, null, null);

						function checkModal(result) {
							if (result === '' || history.state) {
								return;
							}
							if (parseInt(result) > 0) {
								$(".modal-body").html(
										"게시글 " + parseInt(result)
												+ " 번이 등록되었습니다");
							}
							$("#myModal").modal("show");
						}

						$("#regBtn").on("click", function() { //New버튼을 누르면
							self.location = "/board/register_N";
						});

						/* 페이지번호처리 */
						var actionForm = $("#actionForm");
						$(".paginate_button a").on(
								"click",
								function(e) {
									e.preventDefault();
									console.log('click');
									actionForm.find("input[name='pageNum']")
											.val($(this).attr("href"));
									actionForm.submit();
								});

						//게시물 조회를 위한 이벤트 처리
						$(".move")
								.on(
										"click",
										function(e) {
											e.preventDefault();
											actionForm
													.append("<input type='hidden' name='brd_id' value='"
															+ $(this).attr(
																	"href")
															+ "'>");
											actionForm.attr("action",
													"/board/get_N");
											actionForm.submit();
										});

						//검색 버튼 이벤트 처리
						var searchForm = $("#searchForm");
						$("#searchForm button").on("click", function(e) {
							if (!searchForm.find("option:selected").val()) {
								alert("검색종류를 선택하세요");
								return false;
							}

							//없어도댐
							/* if(!searchForm.find("input[name='keyword']").val()){
								alert("키워드를 입력하세요");
								return false;
							} */

							//검색시 1페이지로
							searchForm.find("input[name='pageNum']").val("1");
							e.preventDefault();
							searchForm.submit();
						})
					});
</script>