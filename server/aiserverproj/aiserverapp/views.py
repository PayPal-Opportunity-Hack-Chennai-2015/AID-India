from aiserverapp.models import District 
from rest_framework import viewsets
from aiserverapp.serializers import DistrictSerializer


class DistrictViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = District.objects.all()
    serializer_class = DistrictSerializer
