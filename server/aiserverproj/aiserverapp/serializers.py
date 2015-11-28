from rest_framework import serializers
from aiserverapp.models import District
from aiserverapp.models import Block
from aiserverapp.models import Village
from aiserverapp.models import Centre
from aiserverapp.models import Child
from aiserverapp.models import Skill
from aiserverapp.models import Assessment

class DistrictSerializer(serializers.ModelSerializer):
    class Meta:
        model = District
        fields = ('district_id', 'district_name')

class BlockSerializer(serializers.ModelSerializer):
    class Meta:
        model = Block
        fields = ('district_id', 'block_id', 'block_name')

class VillageSerializer(serializers.ModelSerializer):
    class Meta:
        model = Village
        fields = ('block_id', 'village_id', 'village_name')

class CentreSerializer(serializers.ModelSerializer):
    class Meta:
        model = Centre
        fields = ('village_id', 'centre_id', 'centre_name')

class ChildSerializer(serializers.ModelSerializer):
    class Meta:
        model = Child
        fields = ('centre_id', 'child_id', 'child_name', 'child_std')

class SkillSerializer(serializers.ModelSerializer):
    class Meta:
        model = Skill
        fields = ('skill_id', 'skill_name', 'subject_name')

class AssessmentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Assessment
        fields = ('child_id', 'skill_id', 'is_completed')
