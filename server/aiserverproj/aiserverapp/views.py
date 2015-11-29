import csv

from aiserverapp.models import District
from aiserverapp.models import Block
from aiserverapp.models import Village
from aiserverapp.models import Centre
from aiserverapp.models import Child
from aiserverapp.models import Skill
from aiserverapp.models import Assessment
from django.db.models.manager import Manager
from django.test.testcases import assert_and_parse_html
from rest_framework import viewsets
from django.http import HttpResponse
from rest_framework.response import Response
from rest_framework.decorators import api_view
from aiserverapp.serializers import DistrictSerializer
from aiserverapp.serializers import BlockSerializer
from aiserverapp.serializers import VillageSerializer
from aiserverapp.serializers import CentreSerializer
from aiserverapp.serializers import ChildSerializer
from aiserverapp.serializers import SkillSerializer
from aiserverapp.serializers import AssessmentSerializer
from aiserverapp.centre_helper import consolidate_centre_info
from aiserverapp.assessment_info_fetcher import fetch_assessment_info
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
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename="Report.csv"'
    writer = csv.writer(response)
    #headers = ['District', 'Block', 'EBT Name', 'Centre ID', 'Centre Name', 'No. of children']


    ##########
    # cursor = connection.cursor()
    #cursor.execute('DROP VIEW assessment_view')
    # cursor.execute('SELECT aiserverapp_assessment.child_id_id, aiserverapp_skill.skill_name, '
    #                'aiserverapp_skill.subject_name, aiserverapp_assessment.is_completed FROM aiserverapp_assessment INNER JOIN aiserverapp_skill'
    #                ' WHERE aiserverapp_skill.skill_id = aiserverapp_assessment.skill_id_id')
    #cursor.execute('SELECT aiserverapp_child.centre_id_id, assessment_view.* FROM'
    #              ' aiserverapp_child INNER JOIN assessment_view WHERE aiserverapp_child.child_id = aiserverapp_assessment.child_id_id')

    # for row in cursor.fetchall():
    #     print('ROW:',row)
    #################

    assessment_info_list = fetch_assessment_info()

    assessment_headers = []
    assessment_headers.append('Centre ID')
    assessment_headers.append('District')
    assessment_headers.append('Block')
    assessment_headers.append('Village')
    assessment_headers.append('Centre Name')
    assessment_headers.extend(assessment_info_list[0][1:])
    writer.writerow(assessment_headers)

    for assessment_info in assessment_info_list[1:]:
        centre_id = assessment_info[0]
        centre_info = consolidate_centre_info(centre_id)
        final_assessment_info = []
        final_assessment_info.append(centre_id)
        final_assessment_info.append(centre_info['district_name'])
        final_assessment_info.append(centre_info['block_name'])
        final_assessment_info.append(centre_info['village_name'])
        final_assessment_info.append(centre_info['centre_name'])
        final_assessment_info.extend(assessment_info[1:])
        writer.writerow(final_assessment_info)
        # centre_info =
        # centre_info['centre_id'] = assessment_info[0]
        # for i,header in enum(assessment_headers[2:]):
        #     centre_info[header] = assessment_info[i+2]
        #
        # new_centre_info = consolidate_centre_info(centre_info['centre_id'])
        # for k, v in new_centre_info.items():
        #     centre_info[k] = v


    # centres = Centre.objects.all()
    # for centre in centres:
    #     centre_info = consolidate_centre_info(centre.centre_id)
    #     writer.writerow([centre_info['district_name'], centre_info['block_name'], 'EBT',
    #                      centre_info['centre_id'], centre_info['centre_name'], centre_info['children_count']])

    return response