package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.req.MemberCreateReqDto
import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.dto.resp.MemberListRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {

    private val FOLDER_PATH: String = System.getProperty("user.dir") + "/upload/profile"

    override fun createMember(dto: MemberCreateReqDto): MemberRespDto {
        val member: Member = dto.toEntity()
        val savedMember = memberRepository.save(member)
        return MemberRespDto.fromEntity(savedMember)
    }

    override fun getMemberOrThrow(memberId: Long): Member {
        val member = memberRepository.findById(memberId).orElseThrow{ NotFoundException() }
        return member
    }

    private fun getMemberEntityOrThrow(memberId: Long): Member {
        val member = memberRepository.findById(memberId).orElseThrow{ NotFoundException() }
        return member
    }

    override fun getAllMember(): MemberListRespDto {
        val members = memberRepository.findAll()
        val dtoList = members.map { MemberRespDto.fromEntity(it) }
        return MemberListRespDto(dtoList)
    }

    override fun updateMember(memberId: Long, memberUpdateReqDto: MemberUpdateReqDto, file: MultipartFile?): MemberRespDto {
        var isModified = false
        val member: Member = getMemberEntityOrThrow(memberId)

        if (file != null && !file.isEmpty) {
            member.profileUrl = uploadProfile(memberId, file)
            isModified = true
        }

        if (memberUpdateReqDto.nickname != null) {
            member.nickname = memberUpdateReqDto.nickname
            isModified = true
        }

        if (memberUpdateReqDto.birth != null) {
            member.birth = memberUpdateReqDto.birth
            isModified = true
        }

        val updatedMember = if (isModified) {
            memberRepository.save(member)
        } else {
            member
        }

        return MemberRespDto.fromEntity(updatedMember)
    }

    fun uploadProfile(memberId: Long, file: MultipartFile): String {
        val uploadFolder = File(FOLDER_PATH, memberId.toString())
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }
        val folderPath: String = uploadFolder.absolutePath
        val savePath: Path = Paths.get(folderPath + File.separator + file.originalFilename)

        file.transferTo(savePath)

        return savePath.toString()
    }
}