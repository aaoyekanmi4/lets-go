import React, { useState } from "react";
import { useSelector } from "react-redux";
import moment from "moment";

import { editEventPost } from "./helpers.js";
import "./EventPost.scss";

const EventPost = ({
  postId,
  postBody,
  appUserId,
  author,
  postDate,
  eventId,
  getEventPosts,
}) => {
  const userId = useSelector((state) => {
    return state.user.appUserId;
  });

  const jwtToken = useSelector((state) => {
    return state.user.jwtToken;
  });

  const [showEditView, setShowEditView] = useState(false);

  const [updatedPostBody, setUpdatedPostBody] = useState(postBody);

  const editPost = async (e) => {
    e.preventDefault();

    const updatedPostData = {
      eventId,
      postId,
      postBody: updatedPostBody,
      appUserId,
      author,
      postDate: moment().format("YYYY-MM-DDThh:mm:ss"),
    };

    const response = await editEventPost(jwtToken, updatedPostData);

    if (response.status === 204) {
      await getEventPosts();

      setShowEditView(false);
    } else {
      console.log("there were some errors");
    }
  };

  const displayActionButtons = () => {
    if (userId == appUserId) {
      return (
        <div className="EventPost__action-buttons">
          <button
            className="button-main button-main--danger"
            onClick={() => {
              setShowEditView(true);
            }}
          >
            Edit
          </button>
          <button
            className="button-main button-main--primary"
            onClick={() => {
              //delete
            }}
          >
            Delete
          </button>
        </div>
      );
    }
  };

  const showEditForm = () => {
    return (
      <form className="EventPost" onSubmit={editPost}>
        <p className="EventPost__author">{author}</p>

        <p className="EventPost__post-date">{`Posted ${moment(postDate).format(
          "MM-DD-YYYY"
        )}`}</p>

        <textarea
          className="EventPost__edit-field"
          name="postBody"
          value={updatedPostBody}
          onChange={(e) => {
            setUpdatedPostBody(e.target.value);
          }}
        ></textarea>

        <div className="EventPost__action-buttons">
          <button type="submit" className="button-main button-main--danger">
            Update
          </button>
          <button
            className="button-main button-main--primary"
            type="button"
            onClick={() => {}}
          >
            Cancel
          </button>
        </div>
      </form>
    );
  };

  const showDefaultCard = () => {
    return (
      <div className="EventPost">
        <p className="EventPost__author">{author}</p>

        <p className="EventPost__post-date">{`Posted ${moment(postDate).format(
          "MM-DD-YYYY"
        )}`}</p>

        <p className="EventPost__post-body">{postBody}</p>

        {displayActionButtons()}
      </div>
    );
  };

  const displayContent = () => {
    if (showEditView) {
      return showEditForm();
    }

    return showDefaultCard();
  };

  return displayContent();
};

export default EventPost;
