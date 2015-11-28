from rest_framework import serializers
from aiserverapp.models import District

class DistrictSerializer(serializers.ModelSerializer):
    class Meta:
        model = District
        fields = ('district_id', 'district_name')
