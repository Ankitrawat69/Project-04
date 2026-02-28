package in.co.rays.proj4.controller;

/**
 * ORSView defines application-wide constants for JSP view paths and their
 * corresponding controller URL mappings. These constants are used throughout
 * the project to avoid hard-coded strings in controllers and JSPs.
 *
 * <p>
 * - {@code APP_CONTEXT} is the application context root. <br>
 * - {@code PAGE_FOLDER} is the base folder where JSP pages are located. <br>
 * - Other constants map logical view names (JSP paths) and controller URL
 * patterns used by servlets.
 * </p>
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public interface ORSView {

	/** Application context root. */
	public String APP_CONTEXT = "/ORSProject-04";

	/** Base JSP folder. */
	public String PAGE_FOLDER = "/jsp";

	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimetableCtl";

	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimetableListCtl";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";
	
	public String STAFF__VIEW = PAGE_FOLDER + "/StaffView.jsp";
	public String STAFF_CTL = APP_CONTEXT + "/ctl/StaffCtl";
	
	public String STAFF__LIST_VIEW = PAGE_FOLDER + "/StaffListView.jsp";
	public String STAFF_LIST_CTL = APP_CONTEXT + "/ctl/StaffListCtl";
	
	public String ACCOUNT_VIEW = PAGE_FOLDER + "/AccountView.jsp";
	public String ACCOUNT_CTL = APP_CONTEXT + "/ctl/AccountCtl";
	
	public String ACCOUNT_LIST_VIEW = PAGE_FOLDER + "/AccountListView.jsp";
	public String ACCOUNT_LIST_CTL = APP_CONTEXT + "/ctl/AccountListCtl";
	
	public String HOSTEL_ROOM_VIEW = PAGE_FOLDER + "/HostelRoomView.jsp";
    public String HOSTEL_ROOM_CTL = APP_CONTEXT + "/ctl/HostelRoomCtl";
    
    public String HOSTEL_ROOM_LIST_VIEW = PAGE_FOLDER + "/HostelRoomListView.jsp";
    public String HOSTEL_ROOM_LIST_CTL = APP_CONTEXT + "/ctl/HostelRoomListCtl";
    
    public String DOCTOR_VIEW = PAGE_FOLDER + "/DoctorView.jsp";
    public String DOCTOR_CTL = APP_CONTEXT + "/ctl/DoctorCtl";
    
    public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/DoctorListView.jsp";
    public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/DoctorListCtl";
    

    public String FEEDBACK_CATEGORY_VIEW = PAGE_FOLDER + "/FeedbackCategoryView.jsp";
    public String FEEDBACK_CATEGORY_CTL = APP_CONTEXT + "/ctl/FeedbackCategoryCtl";

    public String FEEDBACK_CATEGORY_LIST_VIEW = PAGE_FOLDER + "/FeedbackCategoryListView.jsp";
    public String FEEDBACK_CATEGORY_LIST_CTL = APP_CONTEXT + "/ctl/FeedbackCategoryListCtl";


    public String NOTIFICATION_VIEW = PAGE_FOLDER + "/NotificationView.jsp";
    public String NOTIFICATION_CTL = APP_CONTEXT + "/ctl/NotificationCtl";

    public String NOTIFICATION_LIST_VIEW = PAGE_FOLDER + "/NotificationListView.jsp";
    public String NOTIFICATION_LIST_CTL = APP_CONTEXT + "/ctl/NotificationListCtl";
    
    public String EVENT_VIEW = PAGE_FOLDER + "/EventView.jsp";
    public String EVENT_CTL = APP_CONTEXT + "/ctl/EventCtl";

    public String EVENT_LIST_VIEW = PAGE_FOLDER + "/EventListView.jsp";
    public String EVENT_LIST_CTL = APP_CONTEXT + "/ctl/EventListCtl";

    public String INSURANCE_VIEW = PAGE_FOLDER + "/InsuranceView.jsp";
    public String INSURANCE_CTL = APP_CONTEXT + "/ctl/InsuranceCtl";

    public String INSURANCE_LIST_VIEW = PAGE_FOLDER + "/InsuranceListView.jsp";
    public String INSURANCE_LIST_CTL = APP_CONTEXT + "/ctl/InsuranceListCtl";
    
	public String DEPARTMENT_VIEW = PAGE_FOLDER + "/DepartmentView.jsp";
	public String DEPARTMENT_CTL = APP_CONTEXT + "/ctl/DepartmentCtl";

	public String DEPARTMENT_LIST_VIEW = PAGE_FOLDER + "/DepartmentListView.jsp";
	public String DEPARTMENT_LIST_CTL = APP_CONTEXT + "/ctl/DepartmentListCtl";

	public String JAVA_DOC = "/ORSProject-04/doc/index.html";
}
