import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { getGroups } from "../../actions";
import { FaBullhorn } from "react-icons/fa";
import { deleteGroup } from "./helpers.js";
import DeleteModal from "../DeleteModal/DeleteModal.js";
import { BsPencil } from "react-icons/bs";
import { FaTrashCan } from "react-icons/fa6";
import "./GroupCard.scss";

const GroupCard = ({ groupId, groupName, setDeleteResultIndicator }) => {
  const navigate = useNavigate();

  const dispatch = useDispatch();

  const user = useSelector((state) => {
    return state.user;
  });

  const [showDeleteGroupModal, setShowDeleteGroupModal] = useState(false);

  const [backendDeleteErrors, setBackendDeleteErrors] = useState([]);

  const onDelete = async () => {
    const response = await deleteGroup(groupId, user.jwtToken);

    if (response.status === 204) {
      await dispatch(getGroups());

      displayResultIndicator("success");
    } else {
      setBackendDeleteErrors(response.errorMessages);
      displayResultIndicator("fail");
    }
  };

  const displayResultIndicator = (type) => {
    setDeleteResultIndicator({ show: true, type: type });
    setShowDeleteGroupModal(false);
  };

  return (
    <>
      <button className="GroupCard" onClick={() => {}}>
        <span className="GroupCard__content">
          <span className="GroupCard__name">{groupName}</span>
          <FaBullhorn className="GroupCard__icon" />
        </span>
        <span
          onClick={() => {
            navigate(`/groups/edit/${groupId}`);
          }}
          className="GroupCard__action-button GroupCard__pencil"
        >
          <BsPencil />
        </span>

        <span
          className="GroupCard__action-button GroupCard__trash"
          onClick={(e) => {
            setDeleteResultIndicator({ show: false, type: "" });
            setShowDeleteGroupModal(true);
            e.stopPropagation();
          }}
        >
          <FaTrashCan />
        </span>
      </button>

      {showDeleteGroupModal ? (
        <DeleteModal
          closeModal={() => {
            setShowDeleteGroupModal(false);
          }}
          message={
            <p className="DeleteCard__text">
              Are you sure you want to delete group
              <span className="DeleteCard__name">{`"${groupName}"?`}</span>
            </p>
          }
          onDelete={onDelete}
        />
      ) : null}
    </>
  );
};

export default GroupCard;
