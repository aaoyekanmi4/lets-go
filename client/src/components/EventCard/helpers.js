import axios from "axios";

import baseUrls from "../../baseUrls";
import getBackendErrorMessages from "../../getBackendErrorMessages";

const saveEvent = async (appUserId, jwtToken, eventData) => {
  try {
    //add venue first since its used as a foreign key in event
    const venueResponse = await axios.post(
      `${baseUrls.database}/api/venue`,
      eventData.venue,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    const savedVenue = venueResponse.data;

    //update the venue in eventId to also include venueId since used as foreign key
    await axios.post(
      `${baseUrls.database}/api/event/user/${appUserId}`,
      { ...eventData, venue: savedVenue },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    return {
      status: 201,
    };
  } catch (e) {
    return {
      status: e.response.status,
      errorMessages: getBackendErrorMessages(e),
    };
  }
};

export { saveEvent };

// import React, { useState } from "react";
// import { useSelector } from "react-redux";
// import { Link, useNavigate } from "react-router-dom";
// import { AiOutlineStar } from "react-icons/ai";
// import moment from "moment";

// import { saveEvent } from "./helpers";
// import AuthenticateModal from "../AuthenticateModal/AuthenticateModal.js";
// import "./EventCard.scss";

// const EventCard = ({
//   imageUrl,
//   dateTime,
//   eventName,
//   venue,
//   sourceId,
//   source,
//   setShowSaveEventResult,
// }) => {
//   const navigate = useNavigate();

//   const user = useSelector((state) => {
//     return state.user;
//   });

//   const [showAuthenticateModal, setShowAuthenticateModal] = useState(false);

//   const [saveEventErrors, setSaveEventErrors] = useState([]);

//   const eventData = {
//     imageUrl,
//     eventName,
//     dateTime,
//     venue,
//     sourceId,
//     source,
//   };

//   const onSaveClick = async (e) => {
//     e.stopPropagation();

//     if (!user) {
//       setShowAuthenticateModal(true);
//     } else {
//       const response = await saveEvent(
//         user.appUserId,
//         user.jwtToken,
//         eventData
//       );
//       console.log(response);

//       if (response.status === 201) {
//         setShowSaveEventResult({ show: true, type: "success" });
//       } else {
//         setShowSaveEventResult({ show: true, type: "fail" });
//       }
//     }
//   };

//   return (
//     <>
//       <button
//         className="EventCard"
//         onClick={() => {
//           navigate(`events/${sourceId}`);
//         }}
//       >
//         <span className="EventCard__image-container">
//           <img src={imageUrl} className="EventCard__image" alt={eventName} />
//         </span>

//         <span className="EventCard__details">
//           <span className="EventCard__month-date">
//             <span className="EventCard__month">
//               {moment(dateTime).format("MMM")}
//             </span>
//             <span className="EventCard__date">{moment(dateTime).date()}</span>
//           </span>
//           <span className="EventCard__info">
//             <h2 className="EventCard__name">{eventName}</h2>
//             <span className="EventCard__detail-text">
//               {venue ? venue.name : null}
//             </span>
//             <span className="EventCard__detail-text">
//               {`${moment(dateTime).format("dddd, MMMM Do YYYY")} at ${moment(
//                 dateTime
//               ).format("h:mm a")}`}
//             </span>
//           </span>

//           <span
//             type="button"
//             className="EventCard__icon-button"
//             onClick={onSaveClick}
//           >
//             <AiOutlineStar className="EventCard__icon" />
//           </span>
//         </span>
//       </button>

//       {showAuthenticateModal ? (
//         <AuthenticateModal
//           closeModal={() => {
//             setShowAuthenticateModal(false);
//           }}
//         />
//       ) : null}
//     </>
//   );
// };

// export default EventCard;
