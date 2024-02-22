import "../css/Room.css"

export default function Room({room}) {

  return (
    <div className={"room-item"}>
      <img
        alt="프로필 사진"
        className="profile_img"
        src={room.profile}
      />
      <p className="room-name">{room.name}</p>
    </div>
  );
}
