package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.subtaskDTO.*;

import java.util.List;

public interface SubtaskService {

    void addSubtask(SubtaskCreateRequest subtask);
    void deleteSubtask(Long id);
    void updateSubtask(SubtaskUpdateRequest updatedSubtask, Long id);
//    task id
    List<SubtaskBasicInfo> getSubtasks(Long id);
    List<SubtaskCompleteInfo> getSubtasksDetails(Long id);
    void addAssignee(AssignRequest assign);
//    subtask id
    SubtaskCompleteInfo getSubtaskDetails(Long id);
}
