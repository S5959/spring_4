package com.choa.s4.board.qna;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.choa.s4.board.BoardDTO;
import com.choa.s4.board.file.BoardFileDTO;
import com.choa.s4.util.Pager;

@Controller
@RequestMapping(value="/qna/**")
public class QnaController {

	@Autowired
	private QnaService qnaService;	

	
	//------------------------------------------------------------------------------------------
	@PostMapping("qnaReply")
	public ModelAndView setReply(BoardDTO boardDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		int result = qnaService.setReply(boardDTO);
		String msg = "Reply Write Fail";
		if(result > 0) {
			msg = "Reply Write Success";
		}
		
		mv.addObject("msg", msg);
		mv.addObject("path", "./qnaList");
		mv.setViewName("common/result");
		
		return mv;
	}
	
	@GetMapping("qnaReply")
	public ModelAndView setReply() throws Exception {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("board", "qna");
		mv.setViewName("board/boardReply");
		
		return mv;
	}
	
	
	
	//------------------------------------------------------------------------------------------
	@PostMapping("qnaUpdate")
	public ModelAndView setUpdate(BoardDTO boardDTO) throws Exception {
		System.out.println("Post Update");
		ModelAndView mv = new ModelAndView();
		
		int result = qnaService.setUpdate(boardDTO);
		System.out.println("result : " + result);
		String msg = "Update Fail";
		if(result > 0) {
			msg = "Update Success";
		}
		
		mv.addObject("msg", msg);
		mv.addObject("path", "./qnaList");
		mv.setViewName("common/result");
		
		return mv;
	}
	
	@GetMapping("qnaUpdate")
	public ModelAndView setUpdate(BoardDTO boardDTO, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		boardDTO = qnaService.getOne(boardDTO);
		
		if(boardDTO != null) {
			mv.addObject("board", "qna");
			mv.addObject("dto", boardDTO);
			mv.setViewName("board/boardUpdate");
		} else {
			mv.addObject("msg", "No Data");
			mv.addObject("path", "./qnaList");
			mv.setViewName("common/result");
		}
		
		return mv;
	}
	
	
	
	//------------------------------------------------------------------------------------------
	@GetMapping("qnaDelete")
	public ModelAndView setDelete(BoardDTO boardDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		int result = qnaService.setDelete(boardDTO);
		String msg = "Delete Fail";
		if(result > 0) {
			msg = "Delete Success";
		}
		
		mv.addObject("msg", msg);
		mv.addObject("path", "./qnaList");
		mv.setViewName("common/result");
		
		return mv;
	}
	
	
	
	//------------------------------------------------------------------------------------------
	@GetMapping("qnaSelect")
	public ModelAndView getOne(BoardDTO boardDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		boardDTO = qnaService.getOne(boardDTO);
			
		if(boardDTO != null) {
			mv.addObject("board", "qna");		
			mv.addObject("dto", boardDTO);
			mv.setViewName("board/boardSelect");
		} else {
			mv.addObject("msg", "No Data");
			mv.addObject("path", "./qnaList");
			mv.setViewName("common/result");
		}	
		
		return mv;
	}
	
	
	
	//------------------------------------------------------------------------------------------
	/* 
	 * 순서
	 * 1) JSP에서 파일 선택
	 * 2) Controller(@GetMapping("fileDown") 으로 들어옴
	 * 3) mv.setViewName("fileDown"); 를 보고 이 이름으로 만들어놓은 객체가 있는지 확인
	 * 4) DispatcherServlet(D.S)를 실행
	 * 5) servelt-context.xml 으로 가서
	 * 6) (있으면) FileDown.java 를 실행 		(order:0)
	 * 	  (없으면) -> /WEB-INF/views/ 에서 실행 	(order:1)
	 * 
	 * */	
	@GetMapping("fileDown")
	public ModelAndView fileDown(BoardFileDTO boardFileDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("board", "qna");
		mv.addObject("fileDTO", boardFileDTO);
		//이 이름으로 만들어놓은 객체가 있는지 확인 -> servlet-context.xml에 객체 생성해둠
		mv.setViewName("fileDown");
		return mv;
	}
	
	
	
	 
	//------------------------------------------------------------------------------------------
	// Summernote
	@PostMapping("summernoteDelete")
	public ModelAndView summernoteDelete(String file, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		boolean result = qnaService.summernoteDelete(file, session);
		
		mv.addObject("msg", result);
		mv.setViewName("common/ajaxResult");		
		return mv;
	}
	
	@PostMapping("summernote")
	public ModelAndView summernote(MultipartFile file, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		String fileName = qnaService.summernote(file, session);
		System.out.println(fileName);
		
		String name = session.getServletContext().getContextPath()+File.separator;
		name = name + "resources" + File.separator + "upload" + File.separator + "qna" + File.separator + fileName;
		System.out.println("name : " + name);
		
		mv.addObject("msg", name);
		mv.setViewName("common/ajaxResult");
		
		return mv;
	}
	//------------------------------------------------------------------------------------------
	
	
	
	//------------------------------------------------------------------------------------------
	@PostMapping("qnaWrite")
	public ModelAndView setInsert(BoardDTO boardDTO, MultipartFile[] files, HttpSession session) throws Exception {
		//NoticeDTO noticeDTO 로 파라미터 받아와도 상관없음
		ModelAndView mv = new ModelAndView();		
		
		for(MultipartFile multipartFile : files) {
			System.out.println(multipartFile.getOriginalFilename());			
		}
		
		int result = qnaService.setInsert(boardDTO, files, session);
		String msg = "Write Fail";
		if(result > 0) {
			msg = "Write Success";
		}
		
		mv.addObject("msg", msg);
		mv.addObject("path", "./qnaList");
		mv.setViewName("common/result");
				
		return mv;
	}
	
	@GetMapping("qnaWrite")
	public ModelAndView setInsert() throws Exception {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("board", "qna");
		
		mv.setViewName("board/boardWrite");
		
		return mv;
	}
	
	
	
	//------------------------------------------------------------------------------------------
	//@RequestMapping(value="qnaList")
	@GetMapping("qnaList")
	public ModelAndView getList(Pager pager) throws Exception {
		System.out.println("Controller QnA List");
		ModelAndView mv = new ModelAndView();
		List<BoardDTO> ar =  qnaService.getList(pager);
		
		//ar = null;	//NullPointerException Test
		BoardDTO boardDTO = ar.get(0);
		QnaDTO qnaDTO = (QnaDTO)boardDTO;
		//System.out.println(qnaDTO.getDepth());
		
		mv.addObject("board", "qna");
		mv.addObject("list", ar);
		mv.addObject("pager", pager);
		mv.setViewName("board/boardList");
		
		return mv;
	}
	
	
}
