import styled from 'styled-components';

export const List = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  color: white;
`

export const ProfileBox = styled.div`
  display: flex;
  width: 300px;
  flex-direction: row;
  align-items: center;
  gap: 10px;
`

export const ButtonBox = styled.div`
  display: flex;
  width: 100px;
  flex-direction: row;
  gap: 10px;
`

export const Button = styled.button`
  width: 50px;
  height: 30px;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 0px;
`

export const ProfileButton = styled.button`
  width: 100px;
  height: 60px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  margin-top: 0px;
`

export const ProfileInfoDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  color: white;
  height: 100px;
`

export const ProfileCenterDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  color: white;
  height: 100px;
`

export const ProfileName = styled.span`
  font-size: 20px;
`

export const ProfileChangeInput = styled.input`
  width: 200px;
  height: 40px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #313338;
`

export const ProfileChangePasswordInput = styled.input`
  width: 400px;
  height: 40px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #313338;
`

export const ProfileChangeDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
`