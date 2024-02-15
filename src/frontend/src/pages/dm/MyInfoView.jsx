import React, { useEffect, useState } from 'react';
import "../../css/Main.css";
import { ProfileButton, ProfileInfoDiv, ProfileName, ProfileCenterDiv, ProfileChangeInput, ProfileChangeDiv, ProfileChangePasswordInput } from '../../styled/Main.jsx';
import { GetUserInfoRequest, PatchChangeProfileRequest, PatchPasswordRequest, PatchUserNameRequest } from '../../Request/userRequest.js';
import AuthStore from '../../store/AuthStore.jsx';
import { logoutRequest } from '../../Request/userRequest.js';
import { useNavigate } from 'react-router-dom';
import Modal from "react-modal";
import upload from "../../assets/icons/upload.svg";

const defaultImage = "https://storage.googleapis.com/remember-harmony/d68c3d6c-4683-4ddf-892b-8a73e3678145";

export const MyInfoView = () => {
    const navigate = useNavigate();
    const [newName, setNewName] = useState('');

    const { setAccessToken, setUserId } = AuthStore();
    const [isImageModalOpen, setImageModalOpen] = useState(false);
    const [profileImage, setProfileImage] = useState(null);

    const [isPasswordModalOpen, setPasswordModalOpen] = useState(false);
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [newPasswordCheck, setNewPasswordCheck] = useState('');

    const [MyInfo, setMyInfo] = useState({});

    const handleNewName = (e) => {
        setNewName(e.target.value);
    }

    const openImageModal = () => {
        setImageModalOpen(true);
    };

    const closeImageModal = () => {
        setProfileImage(null);
        setImageModalOpen(false);
    }

    const openPasswordModal = () => {
        setPasswordModalOpen(true);
    };

    const closePasswordModal = () => {
        setPasswordModalOpen('');
        setImageModalOpen(false);
    }

    const handleOldPassword = (e) => {
        setOldPassword(e.target.value);
    }

    const handleNewPassword = (e) => {
        setNewPassword(e.target.value);
    }

    const handlenewPasswordCheck = (e) => {
        setNewPasswordCheck(e.target.value);
    }

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        // 파일이 선택되면 상태를 업데이트하여 선택한 파일을 저장
        setProfileImage(file);
    }

    const clickedChangeProfile = (e) => {
        e.preventDefault();

        let formData = new FormData();
        formData.append("UserId", AuthStore.getState().USER_ID);
        formData.append("NewProfile", profileImage);

        PatchChangeProfileRequest(formData)
            .then((response) => {
                if (response.status === 200) {
                    alert("프로필 변경 성공");
                    closeImageModal(false);
                }
                else {
                    alert("프로필 변경 실패");
                }
            })
            .catch((error) => {
                console.error("프로필 변경 실패", error);
            });
    }

    const clickedChangePassword = (e) => {
        e.preventDefault();
        if (newPassword === '' || newPasswordCheck === '' || oldPassword === '') {
            alert("빈칸을 채워주세요");
            return;
        }

        if (newPassword !== newPasswordCheck) {
            alert("새로운 비밀번호가 일치하지 않습니다.");
            return;
        }

        PatchPasswordRequest({ userId: AuthStore.getState().USER_ID, password: oldPassword, newPassword: newPassword })
            .then((response) => {
                if (response.status) {
                    alert("비밀번호 변경 성공");
                    closePasswordModal(false);
                }
                else {
                    alert(response.data.description);
                }
            })
            .catch((error) => {
                console.error("비밀번호 변경 실패", error);
            });
    }

    const clickedChangeName = (e) => {
        e.preventDefault();

        if (newName === '') return;

        PatchUserNameRequest({ userId: AuthStore.getState().USER_ID, newName: newName })
            .then((response) => {
                if (response.status === 200)
                    alert("이름 변경 성공");
            })
            .catch((error) => {
                console.error("이름 변경 실패", error);
            });
    }

    const clickedLogout = (e) => {
        e.preventDefault();

        logoutRequest({ userId: AuthStore.getState().USER_ID, email: MyInfo.email })
            .then((response) => {
                if (response.status === 200) {
                    alert("로그아웃 성공");
                    setAccessToken(null);
                    setUserId(null);
                    navigate("/");
                } else {
                    alert("로그아웃 실패");
                }
            })
            .catch((error) => {
                alert("로그아웃 실패");
            });
    }


    useEffect(() => {
        GetUserInfoRequest().then((response) => {
            if (response) {
                setMyInfo(response);
            }
        }).catch((error) => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
        });
    }, []);

    return (
        <div>
            <h1>내 정보</h1>
            <ProfileInfoDiv>
                <img src={(MyInfo.profileUrl !== "") ? MyInfo.profileUrl : defaultImage} alt="profile" style={{ width: '100px', height: '100px', borderRadius: '50%' }} />
                <ProfileButton onClick={openImageModal}>프로필 변경</ProfileButton>
            </ProfileInfoDiv>
            <Modal
                isOpen={isImageModalOpen}
                onRequestClose={closeImageModal}
                contentLabel="Server Add Modal"
                className="server-add-modal-content"
                overlayClassName="server-add-modal-overlay"
            >
                <div className="server-add-modal-header">
                    <h2>이미지 변경</h2>
                    <h4>
                        어떤 이미지로 변경하고 싶으신가요?!
                    </h4>
                </div>
                <div className="server-add-modal-body">
                    <div className="file_img_container">
                        <label htmlFor="fileInput" className="file_img_label">
                            <img
                                alt="프로필 사진"
                                className="file_img"
                                src={profileImage ? URL.createObjectURL(profileImage) : upload}
                            />
                        </label>
                        <input
                            id="fileInput"
                            type="file"
                            accept="image/*"
                            onChange={handleImageChange}
                            style={{ display: "none" }}
                        />
                    </div>
                </div>
                <div className="server-add-modal-footer">
                    <button onClick={clickedChangeProfile}>변경</button>
                    <button onClick={closeImageModal}>취소</button>
                </div>
            </Modal>

            <ProfileInfoDiv>
                <ProfileName>이름: {MyInfo.name}</ProfileName>
                <ProfileChangeDiv>
                    <ProfileChangeInput
                        onChange={handleNewName}>
                    </ProfileChangeInput>
                    <ProfileButton onClick={clickedChangeName}>이름 변경</ProfileButton>
                </ProfileChangeDiv>
            </ProfileInfoDiv>

            <ProfileCenterDiv>
                <ProfileName>이메일: {MyInfo.email}</ProfileName>
            </ProfileCenterDiv>

            <ProfileCenterDiv>
                <ProfileButton onClick={openPasswordModal}>비밀 번호 변경</ProfileButton>
                <ProfileButton onClick={clickedLogout}>로그 아웃</ProfileButton>
            </ProfileCenterDiv>

            <Modal
                isOpen={isPasswordModalOpen}
                onRequestClose={closePasswordModal}
                contentLabel="Profile Change Modal"
                className="profile-change-modal-content"
                overlayClassName="profile-change-modal-overlay">

                <div className="profile-change-modal-header">
                    <h2>패스워드 변경</h2>
                    <h4>
                        아래의 빈칸들을 채워주세요~~
                    </h4>
                </div>
                <form>
                    <ProfileChangeDiv>
                        <ProfileChangePasswordInput
                            placeholder={"기존 비밀번호를 입력해주세요"}
                            type='password'
                            value={oldPassword}
                            onChange={handleOldPassword}
                            required>
                        </ProfileChangePasswordInput>
                    </ProfileChangeDiv>
                    <br></br>
                    <ProfileChangeDiv>
                        <ProfileChangePasswordInput
                            placeholder={"새로운 비밀번호를 입력해주세요"}
                            type='password'
                            value={newPassword}
                            onChange={handleNewPassword}
                            required>
                        </ProfileChangePasswordInput>
                    </ProfileChangeDiv>
                    <br></br>
                    <ProfileChangeDiv>
                        <ProfileChangePasswordInput
                            placeholder={"새로운 비밀번호를 한번 더 입력해주세요"}
                            type='password'
                            value={newPasswordCheck}
                            onChange={handlenewPasswordCheck}
                            required>
                        </ProfileChangePasswordInput>
                    </ProfileChangeDiv>
                    <div className="profile-change-modal-footer">
                        <button onClick={clickedChangePassword}>변경</button>
                        <button onClick={closePasswordModal}>취소</button>
                    </div>
                </form>
            </Modal>
        </div>
    );
}