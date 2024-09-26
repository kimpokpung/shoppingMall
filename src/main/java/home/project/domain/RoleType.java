package home.project.domain;

/**
 * 사용자의 권한 유형을 정의하는 열거형입니다.
 * 이 열거형은 시스템 내에서 사용자가 가질 수 있는 다양한 권한 수준을 나타냅니다.
 */
public enum RoleType {
    /**
     * 일반 사용자를 나타냅니다.
     * 기본적인 사용자 권한을 가집니다.
     */
    user,

    /**
     * 중간 관리자를 나타냅니다.
     * 시스템의 일부 기능에 대한 접근 권한을 가집니다.
     */
    admin,

    /**
     * 센터 관리자를 나타냅니다.
     * 시스템의 모든 기능에 대한 접근 권한을 가집니다.
     */
    center
}