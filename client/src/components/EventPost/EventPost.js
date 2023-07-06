import React from "react";
import moment from "moment";

import "./EventPost.scss";

const EventPost = ({ postId, postBody, appUserId, author, postDate }) => {
  return (
    <div className="EventPost">
      <p className="EventPost__author">{author}</p>
      <p className="EventPost__post-date">{`Posted ${moment(
        postDate
      ).fromNow()}`}</p>
      <p className="EventPost__post-body">{postBody}</p>
    </div>
  );
};

export default EventPost;
