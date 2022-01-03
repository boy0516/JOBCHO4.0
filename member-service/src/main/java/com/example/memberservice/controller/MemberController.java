package com.example.memberservice.controller;

import com.example.memberservice.dto.MemberDto;
import com.example.memberservice.jpa.MemberEntity;
import com.example.memberservice.service.MemberService;
import com.example.memberservice.vo.RequestMember;
import com.example.memberservice.vo.ResponseMember;
import com.example.memberservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/team/{team_num}/member")
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<ResponseMember>> getListMember(@PathVariable("team_num") int team_num){
        List<MemberDto> list = memberService.getListMember(team_num);
        List<ResponseMember> result = new ArrayList<>();
        list.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseMember.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //	get: /team/{team_num}/members/{user_num} =>멤버 정보 반환
    @GetMapping("/{user_num}")
    public ResponseEntity<ResponseMember> getMember(
            @PathVariable("team_num") int team_num,
            @PathVariable("user_num") int user_num){
        MemberDto memberDto = memberService.getMemberByTeamNumAndUserNum(team_num,user_num);
        ResponseMember result;
        try{
            result = new ModelMapper().map(memberDto,ResponseMember.class);
        }catch(Exception e){
            log.info("memberDto is null");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
//
    //	post: /team/{team_num}/members/invite => 팀에 멤버 초대
    @PostMapping("/invite")
    public ResponseEntity<ResponseMember> insertMember(@PathVariable("team_num") int team_num, @RequestBody RequestMember requestMember){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        MemberDto memberDto = mapper.map(requestMember,MemberDto.class);
        memberDto.setTeamNum(team_num);
        log.info(String.valueOf(memberDto));
        MemberEntity memberEntity = memberService.insertMember(memberDto);
        ResponseMember result = mapper.map(memberEntity, ResponseMember.class);
        return (result!=null)? new ResponseEntity<>(result, HttpStatus.CREATED): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//
    //	put: /team/{team_num}/members/{member_num} =>멤버 정보 수정
    @PutMapping("/{member_num}")
    public ResponseEntity<MemberEntity> updateMember(@PathVariable("team_num") int team_num,
                                                 @PathVariable("member_num") int member_num, @RequestBody RequestMember requestMember){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //강력한 매칭 규칙
        MemberDto memberDto = mapper.map(requestMember,MemberDto.class);
        memberDto.setMemberNum(member_num);
        MemberEntity memberEntity = memberService.updateMember(memberDto);

        return (memberEntity!=null)? new ResponseEntity<>(memberEntity, HttpStatus.OK): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//
    //	delete: /team/{team_num}/members/{member_num} => 멤버 정보 삭제
    @DeleteMapping("/{member_num}")
    public ResponseEntity<String> deleteMember(@PathVariable("team_num") int team_num,
                                               @PathVariable("member_num") int member_num){
        int re = memberService.deleteMember(member_num);

        return (re==1)? new ResponseEntity<>("success", HttpStatus.OK): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//
//    //	get: /team/{team_num}/members/withoutmember => 멤버 초대를 위해 이미 초대된 멤버를 제외한 유저 정보
    @GetMapping("/withoutmember")
    public ResponseEntity<List<ResponseUser>> getListWithoutMembers(@PathVariable("team_num") int team_num){
        List<ResponseUser> result = memberService.getListWithoutMembers(team_num);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
//
//
//    @GetMapping("/withoutchatmember")
//    public ResponseEntity<List<MemberVO>> getListWithOutChatMember(@PathVariable("team_num") int team_num,
//                                                                   @RequestParam int chatroom_num){
//        Map<String, Integer> map = new HashMap<>();
//        map.put("team_num", team_num);
//        map.put("chatroom_num", chatroom_num);
//        List<MemberVO> list = service.getListWithOutChatMember(map);
//
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }
//
//
    //	post: /team/{team_num}/members/uploadprofile 프로필 업로드 처리
    @PostMapping("/{member_num}/uploadprofile")
    public ResponseEntity<MemberEntity> uploadProfileImg(MultipartFile[] uploadFile, @PathVariable("member_num") int member_num) {
        System.out.println("run upload..");
        System.out.println(uploadFile);
        String uploadFolder = "C:\\upload";
        UUID uuid = UUID.randomUUID();
        String uploadFileName = null;
        for(MultipartFile multipartFile : uploadFile){
            uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uuid +"_"+ uploadFileName;
            File saveFile = new File(uploadFolder, uploadFileName);

            try{
                multipartFile.transferTo(saveFile);
            } catch(Exception e){
                log.error(e.getMessage());
            }
        }
        System.out.println(uploadFileName);
        MemberDto memberDto = new MemberDto();
        memberDto.setProfileName(uploadFileName);
        memberDto.setMemberNum(member_num);
        MemberEntity result = memberService.updateMemberProfile(memberDto);

        return (result != null) ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
