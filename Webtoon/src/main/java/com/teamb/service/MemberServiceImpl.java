package com.teamb.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.teamb.domain.MemberVO;
import com.teamb.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper membermapper;
	
	@Value("${profile.image.upload.path}")
    private String uploadPath;

    @Override
    public MemberVO getMemberById(String user_id) {
        return membermapper.findByUserId(user_id);
    }

	// 로그인
	@Override
	public MemberVO MemberLogin(MemberVO member) throws Exception {

		return membermapper.MemberLogin(member);
	}

	// 회원가입
	@Override
	public void MemberJoin(MemberVO member) throws Exception {
		membermapper.MemberJoin(member);
	}

	// 회원수정
	@Override
	public void updateMember(MemberVO member) throws Exception {
		membermapper.updateMember(member);
	}

	// 회원탈퇴
	@Override
    public boolean checkPassword(String user_id, String password) {
        // user_id에 해당하는 회원의 실제 비밀번호를 데이터베이스에서 가져옵니다.
        String actualPassword = membermapper.getPasswordByUserId(user_id);
        
        // 비밀번호 일치 여부를 확인합니다.
        return password.equals(actualPassword);
    }
	
	@Override
	@Transactional
	public boolean deleteMemberById(String user_id) {
		int rowsAffected = membermapper.deleteById(user_id);
        return rowsAffected > 0;
	}

	// 아이디중복확인
	@Override
	public int checkId(String user_id) {
		int cnt = membermapper.checkId(user_id);
		return cnt;
	}

	// 닉네임중복확인
	@Override
	public int checkName(String name) {
		int cnt2 = membermapper.checkName(name);
		return cnt2;
	}

	// 프로필사진업로드
	 @Override
	 public boolean uploadProfileImage(String user_id, MultipartFile file, HttpServletRequest request) {
        
		 try {
            String fileName = saveProfileImage(file, user_id, request);

            // 파일 이름을 Member 테이블의 profile_photo에 업데이트
            membermapper.updateProfilePhoto(user_id, fileName);

            return true; // 업로드 성공
        } catch (IOException e) {
            return false; // 업로드 실패
        }
    }

	 private String saveProfileImage(MultipartFile file, String user_id, HttpServletRequest request) throws IOException {
        if (!file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + "_" + originalFileName;
            
            ///
            HttpSession session = request.getSession();
            MemberVO member = (MemberVO) session.getAttribute("member");
            member.setProfile_photo(fileName);
	        session.setAttribute("member", member);
        	///
	        
            String path = request.getServletContext().getRealPath("/resources/img/upload");
            
            System.out.println("path : " + path);
            
            File dest = new File(path, fileName);
            file.transferTo(dest);

            return fileName;
        }
        return null;
    }

	 // 비밀번호 변경
	 @Override
    public void changePassword(String user_id, String new_password) {
        membermapper.updatePassword(user_id, new_password);
    }
	  
	// 패스워드 일치 확인
	@Override
	public int checkCurrentPassword(String user_id, String password) {
		int cnt = membermapper.checkCurrentPassword(user_id, password);
		return cnt;
	}
 
	//회원목록
	@Override
	public List<MemberVO> memberList(){
		return membermapper.memberList();
	}
	
}