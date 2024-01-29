import { Camera } from "react-feather";
import { Controller } from "react-hook-form";

import { Avatar } from "antd";

import { CTAButton, DatePicker, Input, TextButton } from "@/components/atoms";
import withAuth from "@/utils/withAuth";

import { MypageDetailPageStyle } from "./MyDetailPageCommonStyle";
import {
  ProfileImageSectionStyle,
  ProfileFormItemStyle,
  ProfileFormControllerStyle,
} from "./MyProfilePageStyle";
import { useProfilePage } from "./controller";

const MyProfilePage = () => {
  const {
    control,
    profileUrlRegister,
    isDirty,
    profileUrl,
    handleSubmit,
    handleUpload,
    handleResetImage,
    handleCancel,
  } = useProfilePage();

  return (
    <MypageDetailPageStyle>
      <p className="page-title">내 프로필 수정</p>

      <form encType="multipart/form-data" onSubmit={handleSubmit}>
        <ProfileImageSectionStyle>
          <div className="profile-avatar">
            <Avatar size={{ md: 120, lg: 160 }} src={profileUrl} />
            <label className="profile-edit">
              <input
                type="file"
                accept="image/*"
                {...profileUrlRegister}
                onChange={e => {
                  handleUpload(e);
                  profileUrlRegister.onChange(e);
                }}
              />
              <Camera strokeWidth={2} />
            </label>
          </div>

          <button className="profile-delete-btn" onClick={handleResetImage}>
            이미지 삭제
          </button>
        </ProfileImageSectionStyle>

        <div className="profile-inputs">
          <ProfileFormItemStyle>
            <label>닉네임</label>
            <Controller
              name="nickname"
              control={control}
              render={({ field }) => (
                <Input placeholder="닉네임을 입력하세요" {...field} />
              )}
            />
          </ProfileFormItemStyle>

          <ProfileFormItemStyle>
            <label>생년월일</label>
            <Controller
              name="birth"
              control={control}
              render={({ field }) => (
                <DatePicker placeholder="생년월일을 입력하세요" {...field} />
              )}
            />
          </ProfileFormItemStyle>

          <ProfileFormItemStyle>
            <label>이메일</label>
            <Controller
              name="email"
              control={control}
              render={({ field }) => (
                <Input placeholder="이메일을 입력하세요" {...field} />
              )}
            />
          </ProfileFormItemStyle>

          <ProfileFormControllerStyle>
            <CTAButton type="submit" size="lg" fullWidth disabled={!isDirty}>
              수정하기
            </CTAButton>
            <TextButton color="bluishGray500" onClick={handleCancel}>
              취소
            </TextButton>
          </ProfileFormControllerStyle>
        </div>
      </form>
    </MypageDetailPageStyle>
  );
};

export default withAuth(MyProfilePage);

export { loader as myProfilePageLoader } from "./controller";
