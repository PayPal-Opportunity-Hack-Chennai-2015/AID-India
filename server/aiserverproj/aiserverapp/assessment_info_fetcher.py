from django.db import connection
from collections import namedtuple

def fetch_assessment_info():
    cursor = connection.cursor()
    cursor.execute('DROP VIEW ASSESSMENT_VIEW')
    cursor.execute('DROP VIEW FINAL_VIEW')
    cursor.execute('CREATE VIEW ASSESSMENT_VIEW AS SELECT child_id_id, aiserverapp_skill.skill_name, '
					   'aiserverapp_skill.subject_name, aiserverapp_assessment.is_completed FROM aiserverapp_assessment INNER JOIN aiserverapp_skill'
					   ' WHERE aiserverapp_skill.skill_id = aiserverapp_assessment.skill_id_id')
		#cursor.execute(SELECT * FROM ASSESSMENT_TABLE)
    cursor.execute('CREATE VIEW FINAL_VIEW AS SELECT aiserverapp_child.centre_id_id, ASSESSMENT_VIEW.* FROM'
					  ' aiserverapp_child INNER JOIN ASSESSMENT_VIEW WHERE aiserverapp_child.child_id = child_id_id')
					 
		#cursor.execute('SELECT centre_id_id, count(child_id_id) as child_count, skill_name, subject_name, count(is_completed) as completed FROM FINAL_VIEW WHERE is_completed=1 group by centre_id_id, skill_name')
		
    cursor.execute('SELECT centre_id_id, count(child_id_id) as child_count, sum(is_completed) as completed, skill_name FROM FINAL_VIEW group by centre_id_id, skill_name')
		#cursor.execute('DROP VIEW ASSESSMENT_VIEW')
    assessment_info = []
    for row in cursor.fetchall():
        assessment_info.append(row)

    assessment_tuple = namedtuple('assessment_tuple','centre_id child_count passed_number skill_name')
    centre_id_dict = {}
    skill_name_set = set()
    for info in assessment_info:
        print(type(info))
        named_info = assessment_tuple._make(info)
        centre_id = named_info.centre_id
        if centre_id not in centre_id_dict:
            centre_id_dict[centre_id] = {}
        centre_id_dict[centre_id][named_info.skill_name] = named_info.passed_number
        centre_id_dict[centre_id]['child_count'] = named_info.child_count
        skill_name_set.add(named_info.skill_name)
		
    final_list = []
    for centre_id in centre_id_dict:
        row = [centre_id]
        row.append(centre_id_dict[centre_id]['child_count'])
        for skill_name in skill_name_set:
            if skill_name in centre_id_dict[centre_id]:
                row.append(centre_id_dict[centre_id][skill_name])
            else:
                row.append(0)
        final_list.append(row)
    
    final_assessment_info = []	
    for row in final_list:
        final_assessment_info.append(row)
		
    return final_assessment_info
	