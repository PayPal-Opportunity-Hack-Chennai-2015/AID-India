from django.db import models

class District(models.Model):
    district_id = models.IntegerField(primary_key=True,default=0)
    district_name = models.CharField(max_length=200)
    def __str__(self):
        return str(self.district_id)

class Block(models.Model):
    district_id = models.ForeignKey(District)
    block_id = models.IntegerField(primary_key=True,default=0)
    block_name = models.CharField(max_length=200)
    def __str__(self):
        return str(self.block_id)
    
class Village(models.Model):
    block_id = models.ForeignKey(Block)
    village_id = models.IntegerField(primary_key=True,default=0)
    village_name = models.CharField(max_length=200)
    def __str__(self):
        return str(self.village_id)
    
class Centre(models.Model):
    village_id = models.ForeignKey(Village)
    centre_id = models.IntegerField(primary_key=True,default=0)
    centre_name = models.CharField(max_length=200)
    def __str__(self):
        return str(self.centre_id)
    
class Child(models.Model):
    centre_id = models.ForeignKey(Village)
    child_id = models.IntegerField(primary_key=True,default=0)
    child_name = models.CharField(max_length=200)
    child_std = models.IntegerField(default=0)
    def __str__(self):
        return str(self.child_id)
    
class Skill(models.Model):
    skill_id = models.IntegerField(primary_key=True,default=0)
    skill_name = models.CharField(max_length=200)
    subject_name = models.CharField(max_length=200)
    def __str__(self):
        return str(self.skill_id)
    
class Assessment(models.Model):
    child_id = models.ForeignKey(Child)
    skill_id = models.ForeignKey(Skill)
    is_completed = models.IntegerField(default=0)
    
