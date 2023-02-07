package server.team_a.todayhouse.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_DENIED(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_METHOD(false, 2004, "요청이 잘못 되었습니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    SELLER_NOT_MATCH(false, 2011, "수정 가능한 판매자가 아닙니다."),
    FAILED_TO_LOAD_USERS_ROLE(false, 2012, "유저 권한을 불러오지 못했습니다."),
    SHOPNAME_CANNOT_NULL(false, 2013, "판매자의 매장명이 입력되지 않았습니다."),
    FAILED_TO_FIND_USER_ADDRESS(false, 2014, "배달할 주소가 입력되어 있지 않습니다. 먼저 주소를 등록해주세요"),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    FAILED_TO_FIND_USER(false, 2018, "유저를 찾을 수 없습니다."),
    // product 2400
    USERS_PRODUCT_IS_NOT_FOUND(false, 2400, "유저의 상품을 찾을 수 없습니다."),
    FAILED_TO_LOAD_PRODUCT(false, 2401, "상품을 찾을 수 없습니다."),
    NO_PRODUCT_IN_CATEGORY(false, 2402, "카테고리 내 상품이 없습니다."),

    // order 2600 ~ 2649
    FAILED_TO_FIND_ORDER(false, 2600, "주문을 찾을 수 없습니다."),
    FAILED_TO_REFUND(false, 2601, "이미 주문 확정된 주문입니다. 환불이 불가 합니다."),
    // cart 2650 ~ 2699
    FAILED_TO_FIND_CART(false, 2650, "장바구니 내역을 찾을 수 없습니다."),

    /**
     * 2500: JWT 오류
     */
    JWT_TOKEN_ISNULL(false, 2505, "JWT가 없거나 짧습니다."),
    BAD_TYPE_JWT(false, 2504, "JWT가 Bearer 타입이 아닙니다."),
    MALFORMED_JWT(false, 2503, "JWT가 MalForm입니다.."),
    BAD_SIGNATURE_JWT(false, 2502, "JWT Signature가 잘못되었습니다."),
    EXPIRED_JWT(false, 2501, "JWT가 만료되었습니다."),
    INVALID_VALIDATION_REQUEST(false, 2500, "Request Validation Error"),





    POST_USERS_EMPTY_NICKNAME(false,2018,"닉네임을 입력해주세요."),

    POST_USERS_EMPTY_PASSWORD(false,2019,"비밀번호를 입력해주세요."),

    POST_USERS_EXISTS_NICKNAME(false,2020,"중복된 닉네임입니다."),

    POST_REVIEW_EMPTY_BODY(false,2021,"리뷰 본문을 입력해주세요"),

    POST_REVIEW_EMPTY_GRADE(false,2022,"리뷰 점수를 입력해주세요"),

    POST_REVIEW_EXIST(false,2023,"해당 상품에 대한 리뷰를 이미 작성했습니다."),

    POST_REVIEW_PRODUCTID_WRONG(false,2024,"해당하는 상품이 없습니다."),


    PATCH_REVIEW_WRONG(false,2025,"리뷰가 없거나 수정할 권한이 없습니다."),


    GET_PLACEIDX_WRONG(false,2026,"해당하는 공간 게시물이 없습니다."),

    PATCH_PLACE_WRONG(false,2027,"해당 게시물을 수정할 권한이 없습니다."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    SCRAP_NOT_FIND(false, 3300, "저장된 스크랩이 업습니다."),
    CART_NOT_FIND(false, 3400, "해당하는 장바구니 상품 정보가 없습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"닉네임 수정 실패"),

    MODIFY_FAIL_PASSWORD(false,4015,"비밀번호 수정 실패"),

    MODIFY_FAIL_INTRODUCE(false,4016,"한 줄 소개 수정 실패"),

    CREATE_FAIL_REVIEW(false,4017,"리뷰 작성 실패"),

    MODIFY_FAIL_REVIEW(false,4018,"리뷰 수정 실패"),

    MODIFY_FAIL_PLACE(false,4019,"공간 게시물 수정 실패"),


    ADD_FAIL_PLACE_HEART(false,4020,"공간 게시물 좋아요 실패"),


    REMOVE_FAIL_PLACE_HEART(false,4021,"공간 게시물 좋아요 해제 실패"),




    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString(){
        return this.code + " " + this.name();
    }
}
