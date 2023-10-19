// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {IERC721} from "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import {IERC721Metadata} from "@openzeppelin/contracts/token/ERC721/extensions/IERC721Metadata.sol";

interface IRefiqTopics is IERC721, IERC721Metadata {
    event TopicCreated(uint256 topicId, string name, string infoCid);
    event ModeratorAdded(uint256 topicId, address moderator);
    event ModeratorRemoved(uint256 topicId, address moderator);
    event TopicInfoUpdated(uint256 topicId, string infoCid);
    event PostCreated(
        uint256 contentId,
        uint256 topicId,
        address author,
        string contentCid
    );
    event CommentCreated(
        uint256 contentId,
        uint256 parentId,
        address author,
        string contentCid
    );
    event ContentRemoved(uint256 contentId);

    error Unauthorized();
    error DuplicateContent(uint256 contentId);

    function createTopic(
        string calldata name,
        string calldata infoCid
    ) external;

    function addModerator(uint256 topicId, address moderator) external;

    function removeModerator(uint256 topicId, address moderator) external;

    function updateTopicInfo(uint256 topicId, string calldata infoCid) external;

    function createPost(uint256 topicId, string calldata contentCid) external;

    function createComment(
        uint256 parentId,
        string calldata contentCid
    ) external;

    function removeContent(uint256 contentId) external;
}
