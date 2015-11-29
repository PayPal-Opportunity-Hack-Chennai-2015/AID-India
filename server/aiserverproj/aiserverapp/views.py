import csv

from aiserverapp.models import District
from aiserverapp.models import Block
from aiserverapp.models import Village
from aiserverapp.models import Centre
from aiserverapp.models import Child
from aiserverapp.models import Skill
from aiserverapp.models import Assessment
from django.db.models.manager import Manager
from rest_framework import viewsets
from django.http import HttpResponse
from rest_framework.decorators import api_view
from aiserverapp.serializers import DistrictSerializer
from aiserverapp.serializers import BlockSerializer
from aiserverapp.serializers import VillageSerializer
from aiserverapp.serializers import CentreSerializer
from aiserverapp.serializers import ChildSerializer
from aiserverapp.serializers import SkillSerializer
from aiserverapp.serializers import AssessmentSerializer
from django.db import connection
class DistrictViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = District.objects.all()
    serializer_class = DistrictSerializer

class BlockViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Block.objects.all()
    serializer_class = BlockSerializer

class VillageViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Village.objects.all()
    serializer_class = VillageSerializer

class CentreViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Centre.objects.all()
    serializer_class = CentreSerializer

class ChildViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Child.objects.all()
    serializer_class = ChildSerializer
    def get_serializer(self, *args, **kwargs):
        if "data" in kwargs:
            data = kwargs["data"]

            if isinstance(data, list):
                kwargs["many"] = True

        return super(ChildViewSet, self).get_serializer(*args, **kwargs)

class SkillViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Skill.objects.all()
    serializer_class = SkillSerializer

class AssessmentViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Assessment.objects.all()
    serializer_class = AssessmentSerializer

@api_view(['GET'])
def center_based_report(request):
    #response = HttpResponse(content_type='text/csv')
    #response['Content-Disposition'] = 'attachment; filename="Report.csv"'
    #writer = csv.writer(response)
    #headers = ['District', 'Block', 'EBT Name', 'Centre ID', 'Centre Name', 'No. of children']

    #writer.writerow(headers)
    response = HttpResponse("ok")
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

    for row in cursor.fetchall():
        print('ROW:',row)
    #centres = Centre.objects.all()
    #for centre in centres:
    #    centre_info = consolidate_centre_info(centre.centre_id)
    #    writer.writerow([centre_info.district_id, centre_info.district_name])

    return response