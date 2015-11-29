from aiserverapp.models import Child
from aiserverapp.models import Assessment
from aiserverapp.models import Village
from aiserverapp.models import Centre
from aiserverapp.models import Block
from aiserverapp.models import District

def consolidate_centre_info(centre_id):
    print('CENTER:', centre_id)
    centre_info = {'centre_id': centre_id}

    centre = Centre.objects.get(centre_id=centre_id)
    centre_info['centre_name'] = centre.centre_name

    #Get the village
    #centre.village_id is of type Village hence will have to get village_id out of it
    village = Village.objects.get(village_id=centre.village_id.village_id)
    centre_info['village_name'] = village.village_name

    #Get the block
    #village.block_id is of type Block hence will have to get block_id out of it
    block = Block.objects.get(block_id=village.block_id.block_id)
    centre_info['block_name'] = block.block_name

    #Get the district
    #block.district_id is of type District hence will have to get district_id out of it
    district = District.objects.get(district_id=block.district_id.district_id)
    print('DIS', district, type(district), district.district_name)
    centre_info['district_name'] = district.district_name

    # centre_info['children_count'] = 0

    #Get all children in this centre
    # for child in Child.objects.filter(centre_id=centre_id):
    #     centre_info['children_count'] = centre_info['children_count'] + 1
    #     #Get assement of that child
    #     print('CHILD:', child)
    #     for assement in Assessment.objects.filter(child_id=child.child_id):
    #         print('assement:', assement)

    return centre_info