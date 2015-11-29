import csv

from aiserverapp.models import District
from aiserverapp.models import Block
from aiserverapp.models import Village
from aiserverapp.models import Centre
from aiserverapp.models import Child
from aiserverapp.models import Skill
from aiserverapp.models import Assessment
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
    writer.writerow(['First row', 'Foo', 'Bar', 'Baz'])
    writer.writerow(['Second row', 'A', 'B', 'C', '"Testing"', "Here's a quote"])
    return response
