package business.dataset;

import java.util.ArrayList;
import java.util.List;

import com.loylty.application.entity.bo.constants.ProgramType;
import com.loylty.application.entity.bo.constants.Status;
import com.loylty.application.entity.bo.constants.TenantType;
import com.loylty.application.entity.bo.master.Program;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.entity.bo.master.User;

public class TenantDataSet
	{
		public static Tenant getInHouseTenant()
			{
				Tenant tenant = new Tenant();
				tenant.setCorporateName("Loylty Rewardz Private Limited");
				tenant.setName("Loylty Rewardz Private Limited");
				tenant.setIsActive(true);
				tenant.setTenantType(TenantType.IN_HOUSE);
				return tenant;
			}
			
		public static Tenant getBankTenant()
			{
				Tenant tenant = new Tenant();
				tenant.setCorporateName("SBI");
				tenant.setName("State Bank Of India");
				tenant.setIsActive(true);
				tenant.setTenantType(TenantType.BANK);
				return tenant;
			}
			
		public static List<Program> getListOfProgram(int count, String programPrefix, User user)
			{
				List<Program> programs = new ArrayList<Program>();
				int counter = 0;
				while (counter < count)
					{
						Program program = new Program();
						program.setName(programPrefix + " " + counter);
						program.setStatus(Status.ACTIVE);
						program.setCreatedBy(user.getId());
						program.setModifiedBy(user.getId());
						if ( counter % 2 == 0 )
							{
								program.setType(ProgramType.ACRUAL);
							}
						else if ( counter % 3 == 0 )
							{
								program.setType(ProgramType.ACRUAL_REDEMPTION);
							}
						else if ( counter % 5 == 0 )
							{
								program.setType(ProgramType.REDEMPTION);
							}
						else
							{
								program.setType(ProgramType.ACRUAL_REDEMPTION);
							}
						programs.add(program);
						counter = counter + 1;
					}
				return programs;
			}
	}
