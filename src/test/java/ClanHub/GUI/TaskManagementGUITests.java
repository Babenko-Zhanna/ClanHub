package ClanHub.GUI;

import ClanHub.core.TestBase;
import ClanHub.pages.DashBoardPage;
import ClanHub.pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TaskManagementGUITests extends TestBase {

    @BeforeMethod
    public void precondition() {
        new HomePage(app.driver, app.wait).goToLoginPage()
                .loginUser();
    }

    private String taskTitle;

    @Test
    public void taskViewPositiveTest() {
        taskTitle = "TaskToView";
        new DashBoardPage(app.driver, app.wait)
                .createTask(taskTitle, "31-05-2025")
                .viewTheTask(taskTitle)
                .verifyPopupWindow();
    }

    @Test
    public void taskViewOkBtnPositiveTest() {
        taskTitle = "TaskToCheck";
        new DashBoardPage(app.driver, app.wait)
                .createTask(taskTitle, "18-05-2025")
                .viewTheTask(taskTitle)
                .clickOkBtn()
                .verifyPopupWindowIsClosed();
    }

    @Test
    public void taskDeletingWithConfirmationPositiveTest() {
        taskTitle = "TaskToDeleteConfirm";
        new DashBoardPage(app.driver, app.wait)
                .createTask(taskTitle, "26-04-2025")
                .deleteTheTask(taskTitle)
                .verifyTaskIsDeleted(taskTitle);
    }

    @Test
    public void taskStatusUpdateWithConfirmationPositiveTest() {
        String taskName = "TaskToComplete"+System.currentTimeMillis();
        new DashBoardPage(app.driver, app.wait)
                .createTask(taskName, "30-04-2025")
                .updateTheTaskStatus(taskName)
                .verifyTaskIsCompleted(taskName);
    }

    @AfterMethod
    public void postCondition() {
        if(taskTitle != null) {
            new DashBoardPage(app.driver, app.wait)
                    .closeModals()
                    .deleteTheTask(taskTitle);
        }
    }
}
